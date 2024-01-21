/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.customer;

import java.util.Date;
import java.util.List;
import model.address.Address;
import model.review.Review;

/**
 *
 * @author datng
 */
public class Customer {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String gender;
    private Date birthday;
    private String image;
    private short role;
    private List<Review> reviews;
    private List<Address> customerAddresses;

    public Customer(int id, String username, String password, String fullName, String phoneNumber, String email, String gender, Date birthday, String image, short role, List<Review> reviews, List<Address> customerAddresses) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.image = image;
        this.role = role;
        this.reviews = reviews;
        this.customerAddresses = customerAddresses;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Address> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(List<Address> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    
    
    
}
