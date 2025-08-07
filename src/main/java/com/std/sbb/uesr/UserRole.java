package com.std.sbb.uesr;

import lombok.Getter;


@Getter
// enum : 자료형
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}