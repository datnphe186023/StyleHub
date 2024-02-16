/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DAO;
import model.review.ReviewDAO;
import model.size.Size;
import utils.DBContext;

/**
 * @author datng
 */
public class ProductDAO extends DBContext implements DAO<Product> {

    @Override
    public Product get(int id) {
        String sql = "select * from [products] where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setTitle(resultSet.getString("title"));
                product.setInPrice(resultSet.getDouble("inPrice"));
                product.setOutPrice(resultSet.getDouble("outPrice"));
                product.setDescription(resultSet.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<String> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(id));
                return product;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    private List<Product> getProductsFromDatabase(String sql){
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setTitle(resultSet.getString("title"));
                product.setInPrice(resultSet.getDouble("inPrice"));
                product.setOutPrice(resultSet.getDouble("outPrice"));
                product.setDescription(resultSet.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<String> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(resultSet.getInt("id")));
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
    }

    @Override
    public List<Product> getAll() {
        String sql = "select * from [products]";
        return getProductsFromDatabase(sql);
    }

    public List<Product> getBestSeller() {
        String sql = "SELECT TOP 8 p.*, SUM(amount) AS totalSold\n" + "FROM orderDetails od\n" + "JOIN products p ON od.product_id = p.id\n" + "GROUP BY p.id, p.title, p.description, p.inPrice, p.outPrice\n" + "ORDER BY totalSold DESC";
        return getProductsFromDatabase(sql);
    }

    public List<Product> getProductByCategory(String[] categoryList){
        if (categoryList == null || categoryList.length == 0) {
            return getAll();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.id, p.title, p.inPrice, p.outPrice, p.description FROM products p ");
        sql.append("INNER JOIN products_categories pc ON p.id = pc.product_id ");
        sql.append("WHERE pc.category_title IN (");
        for (int i = 0; i < categoryList.length; i++) {
            sql.append("?");
            if (i < categoryList.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(") ");
        sql.append("GROUP BY p.id, p.title, p.inPrice, p.outPrice, p.description ");
        sql.append("HAVING COUNT(DISTINCT pc.category_title) = ").append(categoryList.length);
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < categoryList.length; i++) {
                statement.setString(i + 1, categoryList[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setTitle(resultSet.getString("title"));
                product.setInPrice(resultSet.getDouble("inPrice"));
                product.setOutPrice(resultSet.getDouble("outPrice"));
                product.setDescription(resultSet.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<String> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(resultSet.getInt("id")));
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productList;
    }

    private List<String> getCategoryListForProduct(int productId) throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT * FROM products_categories WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String category = resultSet.getString("category_title");
                    categories.add(category);
                }
            }
        }

        return categories;
    }

    private List<String> getImagesListForProduct(int productId) throws SQLException {
        List<String> images = new ArrayList<>();
        String sql = "SELECT * FROM products_image WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    images.add(resultSet.getString("href"));
                }
            }
        }

        return images;
    }

    public List<Size> getSizeListForProduct(int productId) throws SQLException {
        List<Size> sizes = new ArrayList<>();
        String sql = "SELECT * FROM size WHERE product_id = ? and stock > 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Size size = new Size();
                    size.setProduct_id(productId);
                    size.setSize(resultSet.getInt("size"));
                    size.setStock(resultSet.getInt("stock"));
                    sizes.add(size);
                }
            }
        }

        return sizes;
    }

    public List<Product> getByName(String name) {
        String sql = "select * from [products] where title like ?";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setTitle(resultSet.getString("title"));
                product.setInPrice(resultSet.getDouble("inPrice"));
                product.setOutPrice(resultSet.getDouble("outPrice"));
                product.setDescription(resultSet.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<String> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(resultSet.getInt("id")));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Product> getProductListForPage(List<Product> productList, int start, int end){
        List<Product> productListForPage = new ArrayList<>();
        for (int i = start; i < end; i++){
            productListForPage.add(productList.get(i));
        }
        return  productListForPage;
    }

    public int countLowStockProduct(){
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM products join dbo.size s on products.id = s.product_id where stock < 10";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("countLowStock " + e);
        }
        return count;
    }

    public int countTotalProduct(){
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM products join dbo.size s on products.id = s.product_id";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("countLowStock " + e);
        }
        return count;
    }

    public List<String> getCategory() {
        String sql = "select * from categories";
        List<String> categoryList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                categoryList.add(resultSet.getString(1));
            }
        }catch (Exception e){
            System.out.println("getCategory " + e);
        }
        return categoryList;
    }


    public String getCategoryByName(String name) {
        String sql = "select * from categories where title = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getString(1);
            }
        }catch (Exception e){
            System.out.println("getCatByName " + e);
        }
        return null;
    }

    public void addCategory(String name) {
        String sql = "insert into categories values (?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("addCat " + e);
        }
    }

    public void addProduct(String title, double inPrice, double outPrice, String description){
        String sql = "insert into products values (?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setDouble(2, inPrice);
            statement.setDouble(3, outPrice);
            statement.setString(4, description);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("add product " + e);
        }
    }

    public void addItem(int productId, int size, int stock){
        String sql = "insert into size values (?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setInt(2, size);
            statement.setInt(3,stock);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("add item " + e);
        }
    }

    public void deleteProduct(int id){
        String sizeTableSql = "delete from size where product_id = ?";
        String categoriesTableSql = "delete from products_categories where product_id = ?";
        String imagesTableSql = "delete from products_image where product_id = ?";
        String sql = "delete from products where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sizeTableSql);
            statement.setInt(1, id);
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement(categoriesTableSql);
            statement1.setInt(1, id);
            statement1.executeUpdate();
            PreparedStatement statement2 = connection.prepareStatement(imagesTableSql);
            statement2.setInt(1, id);
            statement2.executeUpdate();
            PreparedStatement statement3 = connection.prepareStatement(sql);
            statement3.setInt(1, id);
            statement3.executeUpdate();
        }catch (Exception e) {
            System.out.println("delete product " + e);
        }
    }

    public void updateItem(int productId, int size, int stock){
        String sql = "update size set stock = ? where product_id = ? and size = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, stock);
            statement.setInt(2, productId);
            statement.setInt(3, size);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("update item " + e);
        }
    }

    public void updateItem(int productId, String imageURL){
        String sql = "insert into products_image values (?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(2, productId);
            statement.setString(1, imageURL);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("update url " + e);
        }
    }

    public void updateCategoryForProduct(int productId, String[] category){
        String deleteSql = "delete from products_categories where product_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, productId);
            statement.executeUpdate();
            String addSql = "insert into products_categories values (?,?)";
            PreparedStatement statement1 = connection.prepareStatement(addSql);
            for (String s : category) {
                statement1.setInt(1, productId);
                statement1.setString(2, s);
                statement1.executeUpdate();
            }
        }catch (Exception e){
            System.out.println("update cat for product " + e);
        }
    }

    public void updateProduct(int productId, String title, double inPrice, double outPrice, String description){
        String sql = "update products set title = ?, inPrice = ?, outPrice = ? , description = ? where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setDouble(2, inPrice);
            statement.setDouble(3, outPrice);
            statement.setString(4, description);
            statement.setInt(5, productId);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("update product " + e);
        }
    }
}

