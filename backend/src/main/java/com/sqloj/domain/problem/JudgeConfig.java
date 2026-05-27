package com.sqloj.domain.problem;

import com.sqloj.domain.shared.exception.DomainException;

public record JudgeConfig(int timeLimitMs, int maxRows, boolean orderSensitive) {

    public static final int DEFAULT_TIME_LIMIT_MS = 3000;
    public static final int DEFAULT_MAX_ROWS = 1000;

    public JudgeConfig {
        if (timeLimitMs <= 0) {
            throw new DomainException("time limit must be positive");
        }
        if (maxRows <= 0) {
            throw new DomainException("max rows must be positive");
        }
    }

    public static JudgeConfig defaultConfig() {
        return new JudgeConfig(DEFAULT_TIME_LIMIT_MS, DEFAULT_MAX_ROWS, false);
    }
}

