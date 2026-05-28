package com.sqloj.interfaces.response;

import com.sqloj.domain.judge.JudgeStatus;

import java.time.LocalDateTime;

public record JudgeResponse(
        Long submissionId,
        JudgeStatus status,
        int score,
        String message,
        String detail,
        long executeTimeMs,
        LocalDateTime judgedAt
) {
}
