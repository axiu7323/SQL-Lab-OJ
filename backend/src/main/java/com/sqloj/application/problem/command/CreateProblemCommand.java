package com.sqloj.application.problem.command;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemStatus;

public record CreateProblemCommand(
        String title,
        String description,
        Long categoryId,
        String categoryName,
        ProblemDifficulty difficulty,
        int score,
        String initSchemaSql,
        String initDataSql,
        String standardSql,
        boolean orderSensitive,
        boolean checkColumnName,
        int timeLimitMs,
        int maxRows,
        ProblemStatus status
) {
}

