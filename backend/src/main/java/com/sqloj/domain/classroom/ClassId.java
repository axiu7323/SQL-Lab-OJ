package com.sqloj.domain.classroom;

import com.sqloj.domain.shared.exception.DomainException;

public record ClassId(Long value) {

    public ClassId {
        if (value == null || value <= 0) {
            throw new DomainException("class id must be positive");
        }
    }

    public static ClassId of(Long value) {
        return new ClassId(value);
    }
}

