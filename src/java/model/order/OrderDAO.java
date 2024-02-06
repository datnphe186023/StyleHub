/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.order;

import model.DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import model.customer.Customer;
import model.cart.Cart;
import model.item.Item;
import utils.DBContext;

import java.time.LocalDate;

/**
 * @author kat1002
 */
public class OrderDAO extends DBContext implements DAO<Order> {

    public void addOrder(Customer customer, Cart cart) {
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();
        try {
            String sql = "insert into [orders] values (?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, customer.getId());
            st.setString(2, date);
            st.setString(3, "purchased-pending");
            st.setDouble(4, cart.getTotalMoney());
            st.executeUpdate();

            String sql1 = "select top 1 id from [orders] order by id desc";
            PreparedStatement st1 = connection.prepareStatement(sql1);
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                for (Item i : cart.getItems()) {
                    String sql2 = "insert into [orderDetails] values(?,?,?,?,?)";
                    PreparedStatement st2 = connection.prepareStatement(sql2);
                    st2.setInt(1, i.getProduct().getId());
                    st2.setInt(2, orderId);
                    st2.setInt(3, i.getSize());
                    st2.setInt(4, i.getQuantity());
                    st2.setDouble(5, i.getPrice());
                    st2.executeUpdate();
                }
            }
            rs = st1.executeQuery();
            if (rs.next()) {
                for (Item i : cart.getItems()) {
                    String updateProductSql = "UPDATE [size] \n" + "                            SET stock = stock - ?\n" + "                            WHERE product_id = ? and size = ?";
                    PreparedStatement statement = connection.prepareStatement(updateProductSql);
                    statement.setInt(1, i.getQuantity());
                    statement.setInt(2, i.getProduct().getId());
                    statement.setInt(3, i.getSize());
                    statement.executeUpdate();
                }
            }


        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public Order get(int id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    public List<Order> getOrderForCustomer(int customerId) {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from orders where customer_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("id"), customerId, resultSet.getDate("created"),
                        resultSet.getString("status"), resultSet.getDouble("total_price"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return orderList;
    }

    public List<Order> getOrderForCustomer(int customerId, String status) {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from orders where customer_id = ? and status like ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, "%" + status + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("id"), customerId, resultSet.getDate("created"),
                        resultSet.getString("status"), resultSet.getDouble("total_price"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return orderList;
    }

    public List<Order> findOrderForCustomer(String keyword, int customerId){
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT distinct o.*\n" +
                "FROM orders o\n" +
                "         JOIN orderDetails od ON o.id = od.order_id\n" +
                "         JOIN products p ON od.product_id = p.id\n" +
                "WHERE o.customer_id = ?\n" +
                "  AND (o.id LIKE ? OR p.title LIKE ?)\n";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order(resultSet.getInt("id"), customerId, resultSet.getDate("created"),
                        resultSet.getString("status"), resultSet.getDouble("total_price"));
                orderList.add(order);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return orderList;
    }

    public List<OrderDetail> getOrderDetailForOrder(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "select * from orderDetails where order_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                OrderDetail orderDetail = new OrderDetail(resultSet.getInt("product_id"), orderId, resultSet.getInt("size"),
                        resultSet.getInt("amount"), resultSet.getDouble("price"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return orderDetails;
    }


    public void cancelOrderForUser(int orderId){
        String sql = "update orders set status = ? where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "cancel-pending");
            statement.setInt(2, orderId);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
