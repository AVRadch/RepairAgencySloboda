package com.my.repairagency007.model.entity;

import java.util.Date;

public class Feedback {

    private int id;

    private int repairerId;

    private Date date;

    private String feedback;

    private int rating;

    private int requestId;

    public int getId() {
        return id;
    }

    public int getRepairerId() {
        return repairerId;
    }

    public Date getDate() {
        return date;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getRating() {
        return rating;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRepairerId(int repairerId) {
        this.repairerId = repairerId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
