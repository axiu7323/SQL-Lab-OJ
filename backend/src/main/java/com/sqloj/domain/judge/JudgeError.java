package com.sqloj.domain.judge;

public record JudgeError(String code, String message, String detail) {

    public static JudgeError none() {
        return new JudgeError("", "", "");
    }

    public static JudgeError of(String code, String message) {
        return new JudgeError(code, message, "");
    }

    public static JudgeError of(String code, String message, String detail) {
        return new JudgeError(code, message, detail == null ? "" : detail);
    }
}

