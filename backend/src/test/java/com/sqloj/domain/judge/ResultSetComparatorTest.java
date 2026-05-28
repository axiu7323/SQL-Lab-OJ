package com.sqloj.domain.judge;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultSetComparatorTest {

    private final ResultSetComparator comparator = new ResultSetComparator();

    @Test
    void sameResultSetShouldMatch() {
        SqlExecutionResult expected = new SqlExecutionResult(List.of("id", "name"), List.of(List.of(1, "Tom")), 1);
        SqlExecutionResult actual = new SqlExecutionResult(List.of("id", "name"), List.of(List.of(1, "Tom")), 1);

        ResultSetDiff diff = comparator.compare(expected, actual, new ResultCompareOptions(true, true));

        assertTrue(diff.matched());
    }

    @Test
    void unorderedRowsShouldMatchWhenOrderIsNotSensitive() {
        SqlExecutionResult expected = new SqlExecutionResult(List.of("id"), List.of(List.of(1), List.of(2)), 1);
        SqlExecutionResult actual = new SqlExecutionResult(List.of("id"), List.of(List.of(2), List.of(1)), 1);

        ResultSetDiff diff = comparator.compare(expected, actual, new ResultCompareOptions(false, true));

        assertTrue(diff.matched());
    }

    @Test
    void differentValueShouldNotMatch() {
        SqlExecutionResult expected = new SqlExecutionResult(List.of("id"), List.of(List.of(1)), 1);
        SqlExecutionResult actual = new SqlExecutionResult(List.of("id"), List.of(List.of(2)), 1);

        ResultSetDiff diff = comparator.compare(expected, actual, new ResultCompareOptions(true, true));

        assertFalse(diff.matched());
    }
}
