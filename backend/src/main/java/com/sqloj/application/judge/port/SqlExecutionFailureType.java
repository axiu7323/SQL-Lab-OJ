package com.sqloj.application.judge.port;

public enum SqlExecutionFailureType {
    BAD_SQL,
    TIMEOUT,
    RUNTIME_ERROR,
    SYSTEM_ERROR
}
