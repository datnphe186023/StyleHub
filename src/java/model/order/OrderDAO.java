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

    public void addOrder(Customer customer, Cart cart, int addressId) {
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();
        try {
            String sql = "insert into [orders] values (?,?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, customer.getId());
            st.setString(2, date);
            st.setString(3, "purchased-pending");
            st.setDouble(4, cart.getTotalMoney());
            st.setInt(5, addressId);
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
        String sql = "select * from orders";
        List<Order> orderList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order(resultSet.getInt("id"), resultSet.getInt("customer_id"),
                        resultSet.getDate("created"), resultSet.getString("status"),
                        resultSet.getDouble("total_price"), resultSet.getInt("address_id"));
                orderList.add(order);
            }
        }catch (Exception e){
            System.out.println("order getAll " + e);
        }
        return orderList;
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
                        resultSet.getString("status"), resultSet.getDouble("total_price"), resultSet.getInt("address_id"));
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
                        resultSet.getString("status"), resultSet.getDouble("total_price"), resultSet.getInt("address_id"));
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
                        resultSet.getString("status"), resultSet.getDouble("total_price"), resultSet.getInt("address_id"));
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
            System.out.println("get order detail for order " + e);
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

    public void cancelOrderForAdmin(int orderId){
        String sql = "update orders set status = ? where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "cancelled");
            statement.setInt(2, orderId);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void updateOrderForAdmin(int orderId){
        String sql = "update orders set status = ? where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "shipped");
            statement.setInt(2, orderId);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public int getNumberOfOrdersByMonth(String date){
        int total = 0;
        String sql = "SELECT COUNT(*) as 'count'\n"
                + "  FROM orders where MONTH(created) = MONTH(?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("OrderDao numberOfOrder " + e);
        }
        return total;
    }

    public List<Order> getOrdersByDate(String date){
        List<Order> orderList = new ArrayList<>();
        String sql = "select orders.* from orders join dbo.customers c on c.id = orders.customer_id\n" +
                "where created = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setCreated(resultSet.getDate("created"));
                order.setStatus(resultSet.getString("status"));
                order.setTotalPrice(resultSet.getDouble("total_price"));
                order.setAddress(resultSet.getInt("address_id"));
                orderList.add(order);
            }
        }catch (SQLException e){
            System.out.println("getOrdersByDate " + e);
        }
        return orderList;
    }

    public List<Order> getOrdersByMonth(int year, int month) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT orders.* FROM orders JOIN dbo.customers c ON c.id = orders.customer_id " +
                "WHERE YEAR(created) = ? AND MONTH(created) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, year);
            statement.setInt(2, month);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setCreated(resultSet.getDate("created"));
                order.setStatus(resultSet.getString("status"));
                order.setTotalPrice(resultSet.getDouble("total_price"));
                order.setAddress(resultSet.getInt("address_id"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println("getOrdersByMonth " + e);
        }
        return orderList;
    }
}
