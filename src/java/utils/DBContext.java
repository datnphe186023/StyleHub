/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author datng
 */

public class DBContext {
 protected Connection connection;
 public DBContext()
 {
 try {
 // Edit URL , username, password to authenticate with your MS SQL Server
 String url = "jdbc:sqlserver://localhost:1433;databaseName= Shoes_Shop;encrypt=true;trustServerCertificate=true;";
 String username = "sa";
 String password = "sa";
 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 connection = DriverManager.getConnection(url, username, password);
 } catch (ClassNotFoundException | SQLException ex) {
 System.out.println(ex);
 }
 }
}

