package com.sqloj.interfaces.response;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemStatus;

import java.util.List;

public record ProblemDetailResponse(
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
        List<ProblemCaseResponse> cases
) {
}

