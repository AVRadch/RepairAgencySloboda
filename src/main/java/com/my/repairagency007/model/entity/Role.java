package com.my.repairagency007.model.entity;

public enum Role {

    MANAGER, CRAFTSMAN, USER, UNREGISTRED;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[--roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
