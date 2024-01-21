/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.order;

import java.util.Date;

/**
 *
 * @author datng
 */
public class Order {
    private int id;
    private int customerId;
    private Date created;
    private double totalPrice;

    public Order() {
    }

    public Order(int id, int customerId, Date created, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.created = created;
        this.totalPrice = totalPrice;
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
    
}
