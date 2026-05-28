package com.sqloj.domain.judge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SqlSafetyCheckerTest {

    private final SqlSafetyChecker sqlSafetyChecker = new SqlSafetyChecker();

    @Test
    void selectAndWithSqlShouldBeAllowed() {
        assertTrue(sqlSafetyChecker.check("SELECT id FROM student;").safe());
        assertTrue(sqlSafetyChecker.check("WITH t AS (SELECT 1 AS id) SELECT id FROM t").safe());
    }

    @Test
    void dangerousSqlShouldBeRejectedWithReason() {
        SqlSafetyCheckResult result = sqlSafetyChecker.check("SELECT * FROM student; DROP TABLE student");

        assertFalse(result.safe());
        assertTrue(result.message().contains("multiple"));
    }

    @Test
    void nonSelectSqlShouldBeRejected() {
        SqlSafetyCheckResult result = sqlSafetyChecker.check("UPDATE student SET name = 'Tom'");

        assertFalse(result.safe());
        assertTrue(result.message().contains("SELECT"));
    }
}
