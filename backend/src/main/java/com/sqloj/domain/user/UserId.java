package com.sqloj.domain.user;

import com.sqloj.domain.shared.exception.DomainException;

public record UserId(Long value) {

    public UserId {
        if (value == null || value <= 0) {
            throw new DomainException("user id must be positive");
        }
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }
}

