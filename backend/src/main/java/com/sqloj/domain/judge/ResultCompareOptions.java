package com.sqloj.domain.judge;

public record ResultCompareOptions(boolean orderSensitive, boolean checkColumnName) {

    public static ResultCompareOptions of(boolean orderSensitive, boolean checkColumnName) {
        return new ResultCompareOptions(orderSensitive, checkColumnName);
    }
}

