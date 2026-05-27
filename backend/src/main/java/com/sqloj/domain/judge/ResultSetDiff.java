package com.sqloj.domain.judge;

public record ResultSetDiff(
        boolean matched,
        String message,
        Integer rowIndex,
        Integer columnIndex,
        Object expectedValue,
        Object actualValue
) {

    public static ResultSetDiff success() {
        return new ResultSetDiff(true, "matched", null, null, null, null);
    }

    public static ResultSetDiff mismatched(
            String message,
            Integer rowIndex,
            Integer columnIndex,
            Object expectedValue,
            Object actualValue
    ) {
        return new ResultSetDiff(false, message, rowIndex, columnIndex, expectedValue, actualValue);
    }
}
