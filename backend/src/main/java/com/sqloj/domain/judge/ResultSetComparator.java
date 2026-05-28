package com.sqloj.domain.judge;

import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ResultSetComparator {

    public ResultSetDiff compare(
            SqlExecutionResult expected,
            SqlExecutionResult actual,
            ResultCompareOptions options
    ) {
        if (expected.columns().size() != actual.columns().size()) {
            return ResultSetDiff.mismatched(
                    "column count mismatch",
                    null,
                    null,
                    expected.columns().size(),
                    actual.columns().size()
            );
        }
        if (options.checkColumnName()) {
            for (int columnIndex = 0; columnIndex < expected.columns().size(); columnIndex++) {
                String expectedColumn = expected.columns().get(columnIndex);
                String actualColumn = actual.columns().get(columnIndex);
                if (!Objects.equals(expectedColumn, actualColumn)) {
                    return ResultSetDiff.mismatched(
                            "column name mismatch",
                            null,
                            columnIndex + 1,
                            expectedColumn,
                            actualColumn
                    );
                }
            }
        }
        if (expected.rows().size() != actual.rows().size()) {
            return ResultSetDiff.mismatched(
                    "row count mismatch",
                    null,
                    null,
                    expected.rows().size(),
                    actual.rows().size()
            );
        }

        List<List<Object>> expectedRows = normalizeRows(expected.rows(), options.orderSensitive());
        List<List<Object>> actualRows = normalizeRows(actual.rows(), options.orderSensitive());
        for (int rowIndex = 0; rowIndex < expectedRows.size(); rowIndex++) {
            List<Object> expectedRow = expectedRows.get(rowIndex);
            List<Object> actualRow = actualRows.get(rowIndex);
            ResultSetDiff rowDiff = compareRow(expectedRow, actualRow, rowIndex + 1);
            if (!rowDiff.matched()) {
                return rowDiff;
            }
        }
        return ResultSetDiff.success();
    }

    private ResultSetDiff compareRow(List<Object> expectedRow, List<Object> actualRow, int rowIndex) {
        if (expectedRow.size() != actualRow.size()) {
            return ResultSetDiff.mismatched(
                    "row column count mismatch",
                    rowIndex,
                    null,
                    expectedRow.size(),
                    actualRow.size()
            );
        }
        for (int columnIndex = 0; columnIndex < expectedRow.size(); columnIndex++) {
            Object expectedValue = expectedRow.get(columnIndex);
            Object actualValue = actualRow.get(columnIndex);
            if (!sameValue(expectedValue, actualValue)) {
                return ResultSetDiff.mismatched(
                        "cell value mismatch",
                        rowIndex,
                        columnIndex + 1,
                        expectedValue,
                        actualValue
                );
            }
        }
        return ResultSetDiff.success();
    }

    private List<List<Object>> normalizeRows(List<List<Object>> rows, boolean orderSensitive) {
        if (orderSensitive) {
            return rows;
        }
        return rows.stream()
                .sorted(Comparator.comparing(this::rowSortKey))
                .toList();
    }

    private String rowSortKey(List<Object> row) {
        return row.stream()
                .map(this::sortValue)
                .reduce((left, right) -> left + "\u001F" + right)
                .orElse("");
    }

    private String sortValue(Object value) {
        if (value == null) {
            return "<NULL>";
        }
        if (value instanceof BigDecimal decimal) {
            return decimal.stripTrailingZeros().toPlainString();
        }
        return value.toString();
    }

    private boolean sameValue(Object expectedValue, Object actualValue) {
        if (expectedValue == null || actualValue == null) {
            return expectedValue == actualValue;
        }
        if (expectedValue instanceof Number && actualValue instanceof Number) {
            return new BigDecimal(expectedValue.toString()).compareTo(new BigDecimal(actualValue.toString())) == 0;
        }
        if (expectedValue instanceof Temporal || actualValue instanceof Temporal) {
            return expectedValue.toString().equals(actualValue.toString());
        }
        return Objects.equals(expectedValue, actualValue);
    }
}

