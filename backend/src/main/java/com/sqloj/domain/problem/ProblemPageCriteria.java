package com.sqloj.domain.problem;

public record ProblemPageCriteria(
        long pageNo,
        long pageSize,
        String keyword,
        Long categoryId,
        ProblemDifficulty difficulty,
        ProblemStatus status,
        boolean enabledOnly
) {

    public ProblemPageCriteria {
        pageNo = pageNo <= 0 ? 1 : pageNo;
        pageSize = pageSize <= 0 ? 10 : Math.min(pageSize, 100);
        keyword = keyword == null ? "" : keyword.trim();
    }

    public static ProblemPageCriteria teacher(
            long pageNo,
            long pageSize,
            String keyword,
            Long categoryId,
            ProblemDifficulty difficulty,
            ProblemStatus status
    ) {
        return new ProblemPageCriteria(pageNo, pageSize, keyword, categoryId, difficulty, status, false);
    }

    public static ProblemPageCriteria student(
            long pageNo,
            long pageSize,
            String keyword,
            Long categoryId,
            ProblemDifficulty difficulty
    ) {
        return new ProblemPageCriteria(pageNo, pageSize, keyword, categoryId, difficulty, ProblemStatus.ENABLED, true);
    }
}

