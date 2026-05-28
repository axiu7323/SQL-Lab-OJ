package com.sqloj.infrastructure.sandbox;

import com.sqloj.application.judge.port.SqlSandbox;
import com.sqloj.application.judge.port.SqlSandboxCallback;
import com.sqloj.application.judge.port.SqlSandboxManager;
import com.sqloj.domain.judge.SqlExecutionResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SandboxDatabaseManager implements SqlSandboxManager {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcSqlExecutor jdbcSqlExecutor;

    public SandboxDatabaseManager(JdbcTemplate jdbcTemplate, JdbcSqlExecutor jdbcSqlExecutor) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcSqlExecutor = jdbcSqlExecutor;
    }

    @Override
    public <T> T executeInSandbox(Long submissionId, SqlSandboxCallback<T> callback) {
        String databaseName = sandboxDatabaseName(submissionId);
        jdbcTemplate.execute("CREATE DATABASE `" + databaseName + "`");
        try {
            SqlExecutionContext context = new SqlExecutionContext(databaseName);
            return callback.execute(new JdbcSqlSandbox(context));
        } finally {
            jdbcTemplate.execute("DROP DATABASE IF EXISTS `" + databaseName + "`");
        }
    }

    private String sandboxDatabaseName(Long submissionId) {
        long safeSubmissionId = submissionId == null ? 0 : submissionId;
        return "judge_" + safeSubmissionId + "_" + System.currentTimeMillis();
    }

    private class JdbcSqlSandbox implements SqlSandbox {

        private final SqlExecutionContext context;

        private JdbcSqlSandbox(SqlExecutionContext context) {
            this.context = context;
        }

        @Override
        public void executeStatement(String sql) {
            jdbcSqlExecutor.executeStatement(context, sql);
        }

        @Override
        public SqlExecutionResult executeQuery(String sql, int maxRows, int timeoutMs) {
            return jdbcSqlExecutor.executeQuery(context, sql, maxRows, timeoutMs);
        }
    }
}
