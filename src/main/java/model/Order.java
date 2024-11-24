package model;

import java.io.*;
import java.util.*;

public class Order implements Serializable {
    private int id;
    private Date date;
    private double amount;
    private String paymentMethod;

    public Order() {
    }

    public Order(int id, Date date, double amount, String paymentMethod) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
