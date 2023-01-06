package com.my.repairagency007.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"email", "firstName", "lastName", "account", "phoneNumber"})
@Builder
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
}
