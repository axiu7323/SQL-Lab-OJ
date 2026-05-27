package com.sqloj.domain.problem;

import com.sqloj.domain.shared.exception.DomainException;

import java.util.Objects;

public record ProblemCase(
        Long id,
        String name,
        SqlScript schemaSql,
        SqlScript initDataSql,
        SqlScript expectedSql,
        boolean sampleCase
) {

    public ProblemCase {
        if (name == null || name.isBlank()) {
            throw new DomainException("problem case name must not be blank");
        }
        name = name.trim();
        Objects.requireNonNull(schemaSql, "schemaSql must not be null");
        Objects.requireNonNull(initDataSql, "initDataSql must not be null");
        Objects.requireNonNull(expectedSql, "expectedSql must not be null");
    }
}

