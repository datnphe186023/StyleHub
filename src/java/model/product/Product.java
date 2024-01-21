/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

import java.util.List;
import model.category.Category;
import model.review.Review;
import model.size.Size;

/**
 *
 * @author datng
 */
public class Product {
    private int id;
    private String title;
    private double inPrice;
    private double outPrice;
    private String description;
    private List<Size> size;
    private List<Category> categories;
    private List<String> images;
    private List<Review> reviews;

    public Product() {
    }

    public Product(int id, String title, double inPrice, double outPrice, String description, List<Size> size, List<Category> categories, List<String> images, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.inPrice = inPrice;
        this.outPrice = outPrice;
        this.description = description;
        this.size = size;
        this.categories = categories;
        this.images = images;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    
    
}
