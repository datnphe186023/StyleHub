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
import model.category.Category;
import model.customer.Customer;
import model.review.Review;
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
                List<Category> categories = getCategoryListForProduct(product.getId());
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
                List<Category> categories = getCategoryListForProduct(product.getId());
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
//        String sql = "SELECT p.id, p.title, p.inPrice, p.outPrice, p.description\n" +
//                "FROM products p\n" +
//                "         INNER JOIN products_categories pc ON p.id = pc.product_id\n" +
//                "WHERE pc.category_title IN ('Men', 'Boot')\n" +
//                "GROUP BY p.id, p.title, p.inPrice, p.outPrice, p.description\n" +
//                "HAVING COUNT(DISTINCT pc.category_title) = 2";
        if (categoryList == null || categoryList.length == 0) {
            throw new IllegalArgumentException("Categories array must not be empty or null");
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
                List<Category> categories = getCategoryListForProduct(product.getId());
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

    private List<Category> getCategoryListForProduct(int productId) throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM products_categories WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Category category = new Category();
                    category.setTitle(resultSet.getString("category_title"));
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

    private List<Size> getSizeListForProduct(int productId) throws SQLException {
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
                List<Category> categories = getCategoryListForProduct(product.getId());
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
}

