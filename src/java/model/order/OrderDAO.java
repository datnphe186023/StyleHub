/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.order;

import model.DAO;

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
            String sql = "insert into [orders] values (?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, customer.getId());
            st.setString(2, date);
            st.setDouble(3, cart.getTotalMoney());
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
}
