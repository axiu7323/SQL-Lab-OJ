package com.sqloj.interfaces.response;

import com.sqloj.domain.submission.SubmissionStatus;

import java.time.LocalDateTime;

public record SubmissionResponse(
        Long id,
        Long userId,
        Long problemId,
        SubmissionStatus status,
        int score,
        String message,
        long executeTimeMs,
        LocalDateTime submittedAt,
        LocalDateTime judgedAt
) {
}

