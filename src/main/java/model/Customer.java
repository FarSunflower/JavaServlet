package model;

import java.io.*;
import java.util.*;

public class Customer implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String image;

    private List<Order> orders;

    public Customer() {
    }

    public Customer(int id, String name, String phone, String address, String image, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
