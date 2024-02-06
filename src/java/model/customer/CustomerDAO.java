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

import model.review.Review;
import utils.DBContext;

/**
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

    public Customer getCustomerById(int id) {
        String sql = "select * from customers where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
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
                customer.setCustomerAddresses(getAddressesListForCustomer(customer.getId()));
                return customer;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private List<Review> getReviewsListForCustomer(int customerId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();
                    review.setId(resultSet.getInt("id"));
                    review.setCustomer(getCustomerById(resultSet.getInt("customer_id")));
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

    private List<String> getAddressesListForCustomer(int customerId) throws SQLException {
        List<String> addresses = new ArrayList<>();
        String sql = "SELECT * FROM customerAddress WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    addresses.add(resultSet.getInt("id") + "%" + resultSet.getString("address"));
                }
            }
        }

        return addresses;
    }

    public boolean updateCustomer(int customerId, String newName, String newPhone, String newEmail, String newGender, String newBirthday) {
        String sql = "UPDATE customers set full_name = ?, phone_number = ?, email = ?, gender = ?, birthday = ? " +
                "where customers.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            Customer customer = getCustomerById(customerId);
            statement.setString(1, newName);
            statement.setString(2, newPhone);
            statement.setString(3, newEmail);
            statement.setString(4, newGender);
            statement.setString(5, newBirthday);
            statement.setInt(6, customerId);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updatePassword(int customerId, String currentPassword, String newPassword, String confirmPassword) {
        Customer customer = getCustomerById(customerId);
        if (!customer.getPassword().equals(currentPassword)) {
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }
        String sql = "Update customers set password = ? where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(2, customerId);
            statement.setString(1, newPassword);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
        return true;
    }

    public void addAddress(int customerId, String address){
        Customer customer = getCustomerById(customerId);
        String sql = "Insert into customerAddress values (?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, address);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void removeAddress(int id){
        String sql = "Delete from customerAddress where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
