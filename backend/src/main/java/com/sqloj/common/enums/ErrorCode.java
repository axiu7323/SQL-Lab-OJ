package com.sqloj.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS("00000", "success"),
    PARAM_ERROR("A0001", "request parameter error"),
    UNAUTHORIZED("A0002", "unauthorized"),
    PERMISSION_DENIED("A0003", "permission denied"),
    NOT_FOUND("A0004", "resource not found"),
    USER_NOT_FOUND("B0001", "user not found"),
    PROBLEM_NOT_FOUND("B0002", "problem not found"),
    PROBLEM_NOT_ENABLED("B0003", "problem is not enabled"),
    SUBMISSION_NOT_FOUND("B0004", "submission not found"),
    SQL_NOT_SAFE("J0001", "sql is not safe"),
    JUDGE_TIMEOUT("J0002", "judge execution timeout"),
    SYSTEM_ERROR("S0001", "system error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

