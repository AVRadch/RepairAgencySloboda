package com.my.repairagency007;

import com.my.repairagency007.DTO.UserDTO;

public class CommonEntity {

    public static UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(1)
                .notification("notification")
                .phoneNumber("+380972866635")
                .account("1000.00")
                .status("registred")
                .password("Aa111111")
                .firstName("Alex")
                .lastName("Petrov")
                .email("asw1@aa.aaa")
                .role("user")
                .build();
    }
}
