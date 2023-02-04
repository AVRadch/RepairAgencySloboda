package com.my.repairagency007.model.DAO;

import com.my.repairagency007.model.entity.User;

public final class DAOTestEntity {

    public static User getTestUser() {
        return User.builder()
                .id(1)
                .notification("notification")
                .phoneNumber("+380972866635")
                .account(100000)
                .status("registred")
                .password("1111")
                .firstName("Alex")
                .lastName("Petrov")
                .email("asw1@aa.aaa")
                .roleId(1)
                .build();
    }
}
