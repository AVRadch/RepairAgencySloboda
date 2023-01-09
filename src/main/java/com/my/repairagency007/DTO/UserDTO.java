package com.my.repairagency007.DTO;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"email", "firstName", "lastName", "account", "phoneNumber"})
@Builder
public class UserDTO {

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
    private String role;
}
