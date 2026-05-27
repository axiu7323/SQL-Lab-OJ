package com.sqloj.domain.user;

import com.sqloj.domain.shared.exception.DomainException;

public record Username(String value) {

    public Username {
        if (value == null || value.isBlank()) {
            throw new DomainException("username must not be blank");
        }
        value = value.trim();
        if (value.length() > 50) {
            throw new DomainException("username length must not exceed 50");
        }
    }

    public static Username of(String value) {
        return new Username(value);
    }
}

