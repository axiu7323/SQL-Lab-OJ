package com.sqloj.infrastructure.sandbox;

import com.sqloj.application.judge.port.SqlExecutionException;
import com.sqloj.application.judge.port.SqlExecutionFailureType;
import com.sqloj.domain.judge.SqlExecutionResult;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.Arrays;

@Component
public class JdbcSqlExecutor {

    private final JdbcTemplate jdbcTemplate;
    private final SqlExecutionResultConverter sqlExecutionResultConverter;

    public JdbcSqlExecutor(JdbcTemplate jdbcTemplate, SqlExecutionResultConverter sqlExecutionResultConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlExecutionResultConverter = sqlExecutionResultConverter;
    }

    public void executeStatement(SqlExecutionContext context, String sql) {
        validateDatabaseName(context.databaseName());
        Arrays.stream(sql.split(";"))
                .map(String::trim)
                .filter(statement -> !statement.isBlank())
                .forEach(statement -> executeSingleStatement(context, statement));
    }

    public SqlExecutionResult executeQuery(SqlExecutionContext context, String sql, int maxRows, int timeoutMs) {
        validateDatabaseName(context.databaseName());
        try {
            return jdbcTemplate.execute((ConnectionCallback<SqlExecutionResult>) connection -> {
                connection.setCatalog(context.databaseName());
                try (Statement statement = connection.createStatement()) {
                    statement.setMaxRows(maxRows);
                    statement.setQueryTimeout(toTimeoutSeconds(timeoutMs));
                    long startTime = System.currentTimeMillis();
                    try (var resultSet = statement.executeQuery(sql)) {
                        return sqlExecutionResultConverter.fromResultSet(
                                resultSet,
                                System.currentTimeMillis() - startTime
                        );
                    }
                }
            });
        } catch (BadSqlGrammarException ex) {
            throw new SqlExecutionException(SqlExecutionFailureType.BAD_SQL, "sql syntax error", ex);
        } catch (RuntimeException ex) {
            Throwable rootCause = rootCause(ex);
            if (rootCause instanceof SQLTimeoutException) {
                throw new SqlExecutionException(SqlExecutionFailureType.TIMEOUT, "sql execution timeout", ex);
            }
            if (rootCause instanceof SQLException) {
                throw new SqlExecutionException(SqlExecutionFailureType.RUNTIME_ERROR, rootCause.getMessage(), ex);
            }
            throw new SqlExecutionException(SqlExecutionFailureType.SYSTEM_ERROR, "sql execution failed", ex);
        }
    }

    private void executeSingleStatement(SqlExecutionContext context, String sql) {
        try {
            jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
                connection.setCatalog(context.databaseName());
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sql);
                    return null;
                }
            });
        } catch (BadSqlGrammarException ex) {
            throw new SqlExecutionException(SqlExecutionFailureType.SYSTEM_ERROR, "sandbox init sql syntax error", ex);
        } catch (RuntimeException ex) {
            throw new SqlExecutionException(SqlExecutionFailureType.SYSTEM_ERROR, "sandbox init sql failed", ex);
        }
    }

    private int toTimeoutSeconds(int timeoutMs) {
        return Math.max(1, (int) Math.ceil(timeoutMs / 1000.0));
    }

    private void validateDatabaseName(String databaseName) {
        if (databaseName == null || !databaseName.matches("[a-zA-Z0-9_]+")) {
            throw new SqlExecutionException(SqlExecutionFailureType.SYSTEM_ERROR, "invalid sandbox database name");
        }
    }

    private Throwable rootCause(Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current;
    }
}
