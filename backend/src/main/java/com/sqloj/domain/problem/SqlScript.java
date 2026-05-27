package com.sqloj.domain.problem;

import com.sqloj.domain.shared.exception.DomainException;

public record SqlScript(String content) {

    public SqlScript {
        if (content == null || content.isBlank()) {
            throw new DomainException("sql script must not be blank");
        }
    }

    public static SqlScript of(String content) {
        return new SqlScript(content);
    }
}

