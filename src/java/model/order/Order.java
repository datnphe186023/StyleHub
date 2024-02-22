/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.order;

import model.product.ProductDAO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author datng
 */
public class Order {
    private int id;
    private int customerId;
    private Date created;
    private String status;
    private double totalPrice;
    private int address;

    public Order() {
    }

    public Order(int id, int customerId, Date created, String status, double totalPrice, int address) {
        this.id = id;
        this.customerId = customerId;
        this.created = created;
        this.status = status;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double calculateProfit() {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ProductDAO productDAO = new ProductDAO();
        double cost = 0.0;
        List<OrderDetail> orderDetails = orderDetailDAO.getByOrderId(getId());
        for (OrderDetail item : orderDetails){
            cost += productDAO.get(item.getProductId()).getInPrice();
        }
        return getTotalPrice() - cost;
    }
}
