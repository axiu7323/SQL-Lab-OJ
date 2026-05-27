package com.sqloj.domain.judge;

import com.sqloj.domain.shared.exception.DomainException;

public record JudgeScore(int value) {

    public JudgeScore {
        if (value < 0 || value > 100) {
            throw new DomainException("judge score must be between 0 and 100");
        }
    }

    public static JudgeScore full() {
        return new JudgeScore(100);
    }

    public static JudgeScore zero() {
        return new JudgeScore(0);
    }
}

