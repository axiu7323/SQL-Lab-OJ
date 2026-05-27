package com.sqloj.domain.judge;

import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmitSql;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class JudgeTask {

    private final JudgeTaskId id;
    private final SubmissionId submissionId;
    private final ProblemId problemId;
    private final SubmitSql submitSql;
    private final LocalDateTime createdAt;

    public JudgeTask(
            JudgeTaskId id,
            SubmissionId submissionId,
            ProblemId problemId,
            SubmitSql submitSql,
            LocalDateTime createdAt
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.submissionId = Objects.requireNonNull(submissionId, "submissionId must not be null");
        this.problemId = Objects.requireNonNull(problemId, "problemId must not be null");
        this.submitSql = Objects.requireNonNull(submitSql, "submitSql must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static JudgeTask create(SubmissionId submissionId, ProblemId problemId, SubmitSql submitSql) {
        return new JudgeTask(
                JudgeTaskId.of(UUID.randomUUID().toString()),
                submissionId,
                problemId,
                submitSql,
                LocalDateTime.now()
        );
    }

    public JudgeTaskId getId() {
        return id;
    }

    public SubmissionId getSubmissionId() {
        return submissionId;
    }

    public ProblemId getProblemId() {
        return problemId;
    }

    public SubmitSql getSubmitSql() {
        return submitSql;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

