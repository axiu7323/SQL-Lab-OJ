package com.sqloj.domain.judge;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SqlSafetyChecker {

    public static final int DEFAULT_MAX_SQL_LENGTH = 10000;

    private static final List<Pattern> FORBIDDEN_PATTERNS = List.of(
            keyword("INSERT"),
            keyword("UPDATE"),
            keyword("DELETE"),
            keyword("DROP"),
            keyword("ALTER"),
            keyword("TRUNCATE"),
            keyword("CREATE"),
            keyword("REPLACE"),
            keyword("GRANT"),
            keyword("REVOKE"),
            keyword("CALL"),
            keyword("EXEC"),
            keyword("LOAD_FILE"),
            phrase("INTO\\s+OUTFILE"),
            phrase("INTO\\s+DUMPFILE"),
            keyword("SLEEP"),
            keyword("BENCHMARK")
    );

    public SqlSafetyCheckResult check(String sql) {
        if (sql == null || sql.isBlank()) {
            return SqlSafetyCheckResult.unsafe("SQL must not be blank");
        }
        if (sql.length() > DEFAULT_MAX_SQL_LENGTH) {
            return SqlSafetyCheckResult.unsafe("SQL length exceeds " + DEFAULT_MAX_SQL_LENGTH);
        }
        String normalizedSql = normalize(sql);
        if (normalizedSql.isBlank()) {
            return SqlSafetyCheckResult.unsafe("SQL must not be blank");
        }
        if (!startsWithSelectOrWith(normalizedSql)) {
            return SqlSafetyCheckResult.unsafe("only SELECT or WITH query is allowed");
        }
        if (hasMultipleStatements(normalizedSql)) {
            return SqlSafetyCheckResult.unsafe("multiple SQL statements are not allowed");
        }
        String upperSql = normalizedSql.toUpperCase(Locale.ROOT);
        for (Pattern pattern : FORBIDDEN_PATTERNS) {
            if (pattern.matcher(upperSql).find()) {
                return SqlSafetyCheckResult.unsafe("forbidden SQL keyword or function detected");
            }
        }
        return SqlSafetyCheckResult.safe(stripTrailingSemicolon(normalizedSql));
    }

    private String normalize(String sql) {
        return sql.trim().replaceAll("\\s+", " ");
    }

    private boolean startsWithSelectOrWith(String sql) {
        String upperSql = sql.toUpperCase(Locale.ROOT);
        return upperSql.startsWith("SELECT ") || upperSql.equals("SELECT") || upperSql.startsWith("WITH ");
    }

    private boolean hasMultipleStatements(String sql) {
        String withoutTrailingSemicolon = stripTrailingSemicolon(sql);
        return withoutTrailingSemicolon.contains(";");
    }

    private String stripTrailingSemicolon(String sql) {
        String stripped = sql.trim();
        while (stripped.endsWith(";")) {
            stripped = stripped.substring(0, stripped.length() - 1).trim();
            break;
        }
        return stripped;
    }

    private static Pattern keyword(String keyword) {
        return Pattern.compile("(^|[^A-Z0-9_])" + keyword + "([^A-Z0-9_]|$)");
    }

    private static Pattern phrase(String phraseRegex) {
        return Pattern.compile("(^|[^A-Z0-9_])" + phraseRegex + "([^A-Z0-9_]|$)");
    }
}

