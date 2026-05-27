package com.sqloj.application.problem.query;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemPageCriteria;
import com.sqloj.domain.problem.ProblemStatus;

public record ProblemPageQuery(
        long pageNo,
        long pageSize,
        String keyword,
        Long categoryId,
        ProblemDifficulty difficulty,
        ProblemStatus status,
        boolean studentVisibleOnly
) {

    public ProblemPageCriteria toCriteria() {
        if (studentVisibleOnly) {
            return ProblemPageCriteria.student(pageNo, pageSize, keyword, categoryId, difficulty);
        }
        return ProblemPageCriteria.teacher(pageNo, pageSize, keyword, categoryId, difficulty, status);
    }
}

