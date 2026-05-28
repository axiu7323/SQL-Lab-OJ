package com.sqloj.application.judge.port;

import com.sqloj.domain.judge.SqlExecutionResult;

public interface SqlSandbox {

    void executeStatement(String sql);

    SqlExecutionResult executeQuery(String sql, int maxRows, int timeoutMs);
}
