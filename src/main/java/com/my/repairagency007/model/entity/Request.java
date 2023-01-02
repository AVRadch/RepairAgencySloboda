package com.my.repairagency007.model.entity;

import java.util.Date;

public class Request {

    private int id;

    private String description;

    private Date date;

    private String completionStatus;

    private String paymentStatus;

    private int totalCost;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
