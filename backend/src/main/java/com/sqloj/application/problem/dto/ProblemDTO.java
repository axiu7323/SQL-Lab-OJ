package com.sqloj.application.problem.dto;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemStatus;

public record ProblemDTO(
        Long id,
        String title,
        Long categoryId,
        String categoryName,
        ProblemDifficulty difficulty,
        ProblemStatus status,
        int score,
        boolean orderSensitive,
        boolean checkColumnName,
        int timeLimitMs,
        int maxRows,
        Double passRate,
        String myStatus
) {
}

