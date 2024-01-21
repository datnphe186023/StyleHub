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
import model.review.Review;
import model.size.Size;
import utils.DBContext;

/**
 *
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
                List<Review> reviews = getReviewsListForProduct(product.getId());
                product.setReviews(reviews);
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
                List<Review> reviews = getReviewsListForProduct(product.getId());
                product.setReviews(reviews);
                list.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
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

    private List<Review> getReviewsListForProduct(int productId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();
                    review.setId(resultSet.getInt("id"));
                    review.setCustomerId(resultSet.getInt("customer_id"));
                    review.setProductId(resultSet.getInt("product_id"));
                    review.setReview(resultSet.getInt("review"));
                    review.setReviewDate(resultSet.getDate("reviewDate"));
                    review.setDetail(resultSet.getString("detail"));
                    reviews.add(review);
                }
            }
        }

        return reviews;
    }

    private List<Size> getSizeListForProduct(int productId) throws SQLException {
        List<Size> sizes = new ArrayList<>();
        String sql = "SELECT * FROM size WHERE product_id = ?";

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

}
