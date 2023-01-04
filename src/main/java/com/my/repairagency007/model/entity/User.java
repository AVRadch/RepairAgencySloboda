package com.my.repairagency007.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String notification;
    private String phoneNumber;
    private int account;
    private String status;
    private transient String password;
    private String firstName;
    private String lastName;
    private String email;
    private int roleId;

    public int getId() {
        return id;
    }

    public String getNotification() {
        return notification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAccount() {
        return account;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notification, phoneNumber, account, status, firstName, lastName, email);
    }

}
