package com.sqloj.interfaces.request;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeacherProblemRequest(
        @NotBlank(message = "title must not be blank")
        String title,
        @NotBlank(message = "description must not be blank")
        String description,
        @NotNull(message = "categoryId must not be null")
        Long categoryId,
        String categoryName,
        @NotNull(message = "difficulty must not be null")
        ProblemDifficulty difficulty,
        @Min(value = 0, message = "score must be greater than or equal to 0")
        @Max(value = 100, message = "score must be less than or equal to 100")
        Integer score,
        @NotBlank(message = "initSchemaSql must not be blank")
        String initSchemaSql,
        @NotBlank(message = "initDataSql must not be blank")
        String initDataSql,
        @NotBlank(message = "standardSql must not be blank")
        String standardSql,
        Boolean orderSensitive,
        Boolean checkColumnName,
        @Min(value = 1, message = "timeLimitMs must be positive")
        Integer timeLimitMs,
        @Min(value = 1, message = "maxRows must be positive")
        Integer maxRows,
        ProblemStatus status
) {
}

