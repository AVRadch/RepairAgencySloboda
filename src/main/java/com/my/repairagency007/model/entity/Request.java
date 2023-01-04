package com.my.repairagency007.model.entity;

import java.util.Date;

public class Request {

    private int id;

    private int user_id;

    private String description;

    private Date date;

    private int completionStatusId;

    private int repairer_id;

    private int paymentStatusId;

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

    public int getCompletionStatusId() {
        return completionStatusId;
    }

    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRepairer_id() {
        return repairer_id;
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

    public void setCompletionStatusId(int completionStatusId) {
        this.completionStatusId = completionStatusId;
    }

    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRepairer_id(int repairer_id) {
        this.repairer_id = repairer_id;
    }
}
