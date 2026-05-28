package com.sqloj.application.judge.dto;

import com.sqloj.domain.judge.JudgeStatus;

import java.time.LocalDateTime;

public record JudgeResultDTO(
        Long submissionId,
        JudgeStatus status,
        int score,
        String message,
        String detail,
        long executeTimeMs,
        LocalDateTime judgedAt
) {
}
