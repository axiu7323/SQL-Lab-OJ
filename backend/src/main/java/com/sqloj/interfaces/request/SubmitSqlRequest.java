package com.sqloj.interfaces.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmitSqlRequest(
        @NotNull(message = "userId must not be null")
        Long userId,
        @NotNull(message = "problemId must not be null")
        Long problemId,
        @NotBlank(message = "submitSql must not be blank")
        String submitSql
) {
}

