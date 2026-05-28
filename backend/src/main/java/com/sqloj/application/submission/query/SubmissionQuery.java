package com.sqloj.application.submission.query;

import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.submission.SubmissionPageCriteria;
import com.sqloj.domain.submission.SubmissionStatus;
import com.sqloj.domain.user.UserId;

import java.time.LocalDateTime;

public record SubmissionQuery(
        long pageNo,
        long pageSize,
        Long studentId,
        Long problemId,
        SubmissionStatus status,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean teacherQuery
) {

    public SubmissionPageCriteria toCriteria() {
        UserId userId = studentId == null ? null : UserId.of(studentId);
        ProblemId queryProblemId = problemId == null ? null : ProblemId.of(problemId);
        if (teacherQuery) {
            return SubmissionPageCriteria.teacher(userId, pageNo, pageSize, queryProblemId, status, startTime, endTime);
        }
        return SubmissionPageCriteria.student(userId, pageNo, pageSize, queryProblemId, status);
    }
}

