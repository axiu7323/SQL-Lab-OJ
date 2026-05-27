package com.sqloj.application.problem.dto;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemStatus;

import java.util.List;

public record ProblemDetailDTO(
        Long id,
        String title,
        String description,
        Long categoryId,
        String categoryName,
        ProblemDifficulty difficulty,
        ProblemStatus status,
        int score,
        boolean orderSensitive,
        boolean checkColumnName,
        int timeLimitMs,
        int maxRows,
        List<ProblemCaseDTO> cases
) {
}

