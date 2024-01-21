/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.address.Address;
import model.review.Review;
import utils.DBContext;

/**
 *
 * @author datng
 */
public class CustomerDAO extends DBContext {

    public Customer getAccount(String username, String pass) throws ClassNotFoundException {
        String sql = "select * from customers where username=? and password=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, pass);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setUsername(resultSet.getString("username"));
                customer.setPassword(resultSet.getString("password"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setGender(resultSet.getString("gender"));
                customer.setBirthday(resultSet.getDate("birthday"));
                customer.setImage(resultSet.getString("image"));
                customer.setRole(resultSet.getShort("role"));
                customer.setReviews(getReviewsListForCustomer(customer.getId()));
                customer.setCustomerAddresses(getAddressesListForCustomer(customer.getId()));
                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
        public List<Review> getReviewsListForCustomer(int customerId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
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

    private List<Address> getAddressesListForCustomer(int customerId) throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM customerAddress WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Address address = new Address();
                    address.setId(resultSet.getInt("id"));
                    address.setCustomerId(resultSet.getInt("customer_id"));
                    address.setAddress(resultSet.getString("address"));
                    addresses.add(address);
                }
            }
        }

        return addresses;
    }
}
