package com.sqloj.domain.submission;

import com.sqloj.domain.shared.exception.DomainException;

public record SubmissionId(Long value) {

    public SubmissionId {
        if (value == null || value <= 0) {
            throw new DomainException("submission id must be positive");
        }
    }

    public static SubmissionId of(Long value) {
        return new SubmissionId(value);
    }
}

