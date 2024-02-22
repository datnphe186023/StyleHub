/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.order;

import model.DAO;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import utils.DBContext;

/**
 *
 * @author kat1002
 */
public class OrderDetailDAO extends DBContext implements DAO<OrderDetail>{


    @Override
    public OrderDetail get(int id) {
        return null;
    }

    @Override
    public List<OrderDetail> getAll() {
        return null;
    }

    public List<OrderDetail> getByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "select * from orderDetails where order_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                OrderDetail orderDetail = new OrderDetail(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4),
                        resultSet.getDouble(5));
                orderDetails.add(orderDetail);
            }
        }catch (Exception e){
            System.out.println("orderDetails Dao " + e);
        }
        return orderDetails;
    }
}
