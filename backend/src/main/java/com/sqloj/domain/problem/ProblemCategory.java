package com.sqloj.domain.problem;

import com.sqloj.domain.shared.exception.DomainException;

public record ProblemCategory(Long id, String name) {

    public ProblemCategory {
        if (id == null || id <= 0) {
            throw new DomainException("problem category id must be positive");
        }
        if (name == null || name.isBlank()) {
            throw new DomainException("problem category name must not be blank");
        }
        name = name.trim();
    }
}

