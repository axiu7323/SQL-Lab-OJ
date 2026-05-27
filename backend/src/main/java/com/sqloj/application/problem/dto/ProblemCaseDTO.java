package com.sqloj.application.problem.dto;

public record ProblemCaseDTO(
        Long id,
        String name,
        String schemaSql,
        String initDataSql,
        String standardSql,
        boolean sampleCase
) {
}

