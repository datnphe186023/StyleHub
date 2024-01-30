/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.item;
import model.product.Product;
/**
 *
 * @author datng
 */
public class Item {
    private Product product;
    private int quantity;
    private double price;

    private int size;
    
    public Item(){
        
    }

    public Item(Product product, int quantity, double price, int size) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
