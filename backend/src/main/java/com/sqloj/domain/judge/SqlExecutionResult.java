package com.sqloj.domain.judge;

import com.sqloj.domain.shared.exception.DomainException;

import java.util.List;

public record SqlExecutionResult(List<String> columns, List<List<Object>> rows, long executeTimeMs) {

    public SqlExecutionResult {
        if (executeTimeMs < 0) {
            throw new DomainException("execute time must not be negative");
        }
        columns = columns == null ? List.of() : List.copyOf(columns);
        rows = rows == null ? List.of() : rows.stream()
                .map(row -> row == null ? List.of() : List.copyOf(row))
                .toList();
    }
}

