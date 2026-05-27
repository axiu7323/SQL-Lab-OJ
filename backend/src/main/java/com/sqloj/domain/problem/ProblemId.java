package com.sqloj.domain.problem;

import com.sqloj.domain.shared.exception.DomainException;

public record ProblemId(Long value) {

    public ProblemId {
        if (value == null || value <= 0) {
            throw new DomainException("problem id must be positive");
        }
    }

    public static ProblemId of(Long value) {
        return new ProblemId(value);
    }
}

