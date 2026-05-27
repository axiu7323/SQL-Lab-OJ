package com.sqloj.interfaces.response;

public record ProblemCaseResponse(
        Long id,
        String name,
        String schemaSql,
        String initDataSql,
        String standardSql,
        boolean sampleCase
) {
}

