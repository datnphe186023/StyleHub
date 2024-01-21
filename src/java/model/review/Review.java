/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.review;

import java.util.Date;

/**
 *
 * @author datng
 */
public class Review {
    private int id;
    private int customerId;
    private int productId;
    private int review;
    private Date reviewDate;
    private String detail;

    public Review() {
    }

    public Review(int id, int customerId, int productId, int review, Date reviewDate, String detail) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.review = review;
        this.reviewDate = reviewDate;
        this.detail = detail;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
     
}
