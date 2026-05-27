package com.sqloj.domain.judge;

import com.sqloj.domain.shared.exception.DomainException;

public record JudgeTaskId(String value) {

    public JudgeTaskId {
        if (value == null || value.isBlank()) {
            throw new DomainException("judge task id must not be blank");
        }
    }

    public static JudgeTaskId of(String value) {
        return new JudgeTaskId(value);
    }
}

