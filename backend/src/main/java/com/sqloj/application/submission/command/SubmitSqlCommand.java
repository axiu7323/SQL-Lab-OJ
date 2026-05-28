package com.sqloj.application.submission.command;

public record SubmitSqlCommand(Long userId, Long problemId, String submitSql) {
}

