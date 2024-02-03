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
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setInPrice(rs.getDouble("inPrice"));
                product.setOutPrice(rs.getDouble("outPrice"));
                product.setDescription(rs.getString("description"));
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


    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "select * from [products]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setInPrice(rs.getDouble("inPrice"));
                product.setOutPrice(rs.getDouble("outPrice"));
                product.setDescription(rs.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<Category> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(rs.getInt("id")));
                list.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getBestSeller() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 8 p.*, SUM(amount) AS totalSold\n" + "FROM orderDetails od\n" + "JOIN products p ON od.product_id = p.id\n" + "GROUP BY p.id, p.title, p.description, p.inPrice, p.outPrice\n" + "ORDER BY totalSold DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setInPrice(rs.getDouble("inPrice"));
                product.setOutPrice(rs.getDouble("outPrice"));
                product.setDescription(rs.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<Category> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(rs.getInt("id")));
                list.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getCategory(List<String> categoryList){
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.id, p.title\n" +
                "FROM products p\n" +
                "WHERE EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM products_categories pc1\n" +
                "    JOIN categories c1 ON pc1.category_title = c1.title\n" +
                "    WHERE p.id = pc1.product_id AND c1.title = ?\n" +
                ")\n" +
                "AND EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM products_categories pc2\n" +
                "    JOIN categories c2 ON pc2.category_title = c2.title\n" +
                "    WHERE p.id = pc2.product_id AND c2.title = ?\n" +
                ");";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet;
            for(int i = 0 ; i < categoryList.size(); i++){
                statement.setString(i+1, categoryList.get(i));
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()){
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
        } catch (Exception e){
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
        String sql = "select * from [products] where title=?";
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setInPrice(rs.getDouble("inPrice"));
                product.setOutPrice(rs.getDouble("outPrice"));
                product.setDescription(rs.getString("description"));
                List<Size> sizes = getSizeListForProduct(product.getId());
                product.setSize(sizes);
                List<Category> categories = getCategoryListForProduct(product.getId());
                product.setCategories(categories);
                List<String> images = getImagesListForProduct(product.getId());
                product.setImages(images);
                ReviewDAO reviewDAO = new ReviewDAO();
                product.setReviews(reviewDAO.getReviewsForProduct(rs.getInt("id")));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
