package com.sqloj.application.judge.port;

public class SqlExecutionException extends RuntimeException {

    private final SqlExecutionFailureType failureType;

    public SqlExecutionException(SqlExecutionFailureType failureType, String message) {
        super(message);
        this.failureType = failureType;
    }

    public SqlExecutionException(SqlExecutionFailureType failureType, String message, Throwable cause) {
        super(message, cause);
        this.failureType = failureType;
    }

    public SqlExecutionFailureType getFailureType() {
        return failureType;
    }
}
