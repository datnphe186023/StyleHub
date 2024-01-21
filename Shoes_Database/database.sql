USE [master]
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'Shoes_Shop')
BEGIN
	ALTER DATABASE [Shoes_Shop] SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE [Shoes_Shop] SET ONLINE;
	DROP DATABASE [Shoes_Shop];
END

GO

CREATE DATABASE [Shoes_Shop]
GO

USE [Shoes_Shop]
GO

/*******************************************************************************
	Drop tables if exists
*******************************************************************************/
DECLARE @sql nvarchar(MAX) 
SET @sql = N'' 

SELECT @sql = @sql + N'ALTER TABLE ' + QUOTENAME(KCU1.TABLE_SCHEMA) 
    + N'.' + QUOTENAME(KCU1.TABLE_NAME) 
    + N' DROP CONSTRAINT ' -- + QUOTENAME(rc.CONSTRAINT_SCHEMA)  + N'.'  -- not in MS-SQL
    + QUOTENAME(rc.CONSTRAINT_NAME) + N'; ' + CHAR(13) + CHAR(10) 
FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS RC 

INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KCU1 
    ON KCU1.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG  
    AND KCU1.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA 
    AND KCU1.CONSTRAINT_NAME = RC.CONSTRAINT_NAME 

EXECUTE(@sql) 

GO
DECLARE @sql2 NVARCHAR(max)=''

SELECT @sql2 += ' Drop table ' + QUOTENAME(TABLE_SCHEMA) + '.'+ QUOTENAME(TABLE_NAME) + '; '
FROM   INFORMATION_SCHEMA.TABLES
WHERE  TABLE_TYPE = 'BASE TABLE'

Exec Sp_executesql @sql2 
GO 
CREATE TABLE [products] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [title] nvarchar(255),
  [inPrice] money,
  [outPrice] money,
  [description] nvarchar(255)
)
GO

CREATE TABLE [size] (
  [product_id] integer foreign key references [products]([id]),
  [size] int,
  [stock] integer,
  PRIMARY KEY ([product_id], [size])
)
GO

CREATE TABLE [products_image] (
  [href] nvarchar(255) PRIMARY KEY,
  [product_id] integer
)
GO

CREATE TABLE [products_categories] (
  [product_id] integer,
  [category_title] nvarchar(255),
  PRIMARY KEY ([product_id], [category_title])
)
GO

CREATE TABLE [categories] (
  [title] nvarchar(255) PRIMARY KEY
)
GO

CREATE TABLE [orders] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [customer_id] integer,
  [created] date,
  [total_price] money
)
GO

CREATE TABLE [orderDetails] (
  [product_id] integer,
  [order_id] integer,
  [amount] integer,
  [price] money,
  PRIMARY KEY ([product_id], [order_id])
)
GO

CREATE TABLE [customers] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [username] nvarchar(255),
  [password] nvarchar(255),
  [full_name] nvarchar(255),
  [phone_number] nvarchar(20),
  [email] nvarchar(255),
  [gender] nvarchar(255),
  [birthday] date,
  [image] nvarchar(255),
  [role] smallint
)
GO

CREATE TABLE [customerAddress] (
  [id] integer IDENTITY(1, 1),
  [customer_id] integer,
  [address] nvarchar(255),
  PRIMARY KEY ([id])
)
GO

CREATE TABLE [reviews] (
  [id] integer PRIMARY KEY IDENTITY(1, 1),
  [customer_id] integer,
  [product_id] integer,
  [review] integer,
  [reviewDate] date,
  [detail] text
)
GO

ALTER TABLE [products_image] ADD FOREIGN KEY ([product_id]) REFERENCES [products] ([id])
GO

ALTER TABLE [products_categories] ADD FOREIGN KEY ([product_id]) REFERENCES [products] ([id])
GO

ALTER TABLE [products_categories] ADD FOREIGN KEY ([category_title]) REFERENCES [categories] ([title])
GO

ALTER TABLE [orderDetails] ADD FOREIGN KEY ([product_id]) REFERENCES [products] ([id])
GO

ALTER TABLE [orderDetails] ADD FOREIGN KEY ([order_id]) REFERENCES [orders] ([id])
GO

ALTER TABLE [reviews] ADD FOREIGN KEY ([customer_id]) REFERENCES [customers] ([id])
GO

ALTER TABLE [reviews] ADD FOREIGN KEY ([product_id]) REFERENCES [products] ([id])
GO

ALTER TABLE [customerAddress] ADD FOREIGN KEY ([customer_id]) REFERENCES [customers] ([id])
GO