package com.sqloj.domain.submission;

import com.sqloj.domain.shared.exception.DomainException;

public record SubmitSql(String value) {

    public static final int MAX_LENGTH = 10000;

    public SubmitSql {
        if (value == null || value.isBlank()) {
            throw new DomainException("submit sql must not be blank");
        }
        value = value.trim();
        if (value.length() > MAX_LENGTH) {
            throw new DomainException("submit sql length must not exceed " + MAX_LENGTH);
        }
    }

    public static SubmitSql of(String value) {
        return new SubmitSql(value);
    }
}

