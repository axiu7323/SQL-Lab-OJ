package com.sqloj.domain.judge;

public record SqlSafetyCheckResult(boolean safe, String message, String normalizedSql) {

    public static SqlSafetyCheckResult safe(String normalizedSql) {
        return new SqlSafetyCheckResult(true, "safe", normalizedSql);
    }

    public static SqlSafetyCheckResult unsafe(String message) {
        return new SqlSafetyCheckResult(false, message, "");
    }
}

