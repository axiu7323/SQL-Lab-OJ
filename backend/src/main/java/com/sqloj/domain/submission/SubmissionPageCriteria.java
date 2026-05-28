package com.sqloj.domain.submission;

import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.user.UserId;

import java.time.LocalDateTime;

public record SubmissionPageCriteria(
        long pageNo,
        long pageSize,
        UserId studentId,
        ProblemId problemId,
        SubmissionStatus status,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public SubmissionPageCriteria {
        pageNo = pageNo <= 0 ? 1 : pageNo;
        pageSize = pageSize <= 0 ? 10 : Math.min(pageSize, 100);
    }

    public static SubmissionPageCriteria student(
            UserId studentId,
            long pageNo,
            long pageSize,
            ProblemId problemId,
            SubmissionStatus status
    ) {
        return new SubmissionPageCriteria(pageNo, pageSize, studentId, problemId, status, null, null);
    }

    public static SubmissionPageCriteria teacher(
            UserId studentId,
            long pageNo,
            long pageSize,
            ProblemId problemId,
            SubmissionStatus status,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        return new SubmissionPageCriteria(pageNo, pageSize, studentId, problemId, status, startTime, endTime);
    }
}

