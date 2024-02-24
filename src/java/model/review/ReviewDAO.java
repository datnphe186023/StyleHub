package model.review;

import com.sun.security.jgss.GSSUtil;
import model.customer.Customer;
import model.customer.CustomerDAO;
import utils.DBContext;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO extends DBContext {
    public List<Review> getReviewsForProduct(int productId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();
                    review.setId(resultSet.getInt("id"));
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customer customer = customerDAO.getCustomerById(resultSet.getInt("customer_id"));
                    review.setCustomer(customer);
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

    public int getNumberReviews(int review, int productId) throws SQLException {
        List<Review> reviews = getReviewsForProduct(productId);
        String sql = "SELECT * FROM reviews WHERE product_id = ? and review = ?";
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setInt(2, review);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                count++;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return count;
    }

    public Review getReviewForAdmin(int orderId, int productId){
        String sql = "select * from reviews where order_id = " + orderId + " and product_id = " + productId;
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Review review = new Review();
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerById(resultSet.getInt("customer_id"));
                review.setCustomer(customer);
                review.setProductId(resultSet.getInt("product_id"));
                review.setReview(resultSet.getInt("review"));
                review.setReviewDate(resultSet.getDate("reviewDate"));
                review.setDetail(resultSet.getString("detail"));
                review.setId(resultSet.getInt("id"));
                return review;
            }
        }catch (Exception e){
            System.out.println("getReviewForAdmin " + e);
        }
        return null;
    }
}
