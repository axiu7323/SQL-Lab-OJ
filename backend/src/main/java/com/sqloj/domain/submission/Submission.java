package com.sqloj.domain.submission;

import com.sqloj.domain.judge.JudgeError;
import com.sqloj.domain.judge.JudgeResult;
import com.sqloj.domain.judge.JudgeScore;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.shared.exception.DomainException;
import com.sqloj.domain.user.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Submission {

    private final SubmissionId id;
    private final UserId userId;
    private final ProblemId problemId;
    private final SubmitSql submitSql;
    private final LocalDateTime submittedAt;
    private SubmissionStatus status;
    private JudgeScore score;
    private JudgeError error;
    private LocalDateTime judgedAt;

    public Submission(
            SubmissionId id,
            UserId userId,
            ProblemId problemId,
            SubmitSql submitSql,
            SubmissionStatus status,
            JudgeScore score,
            JudgeError error,
            LocalDateTime submittedAt,
            LocalDateTime judgedAt
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.problemId = Objects.requireNonNull(problemId, "problemId must not be null");
        this.submitSql = Objects.requireNonNull(submitSql, "submitSql must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.score = Objects.requireNonNull(score, "score must not be null");
        this.error = Objects.requireNonNull(error, "error must not be null");
        this.submittedAt = Objects.requireNonNull(submittedAt, "submittedAt must not be null");
        this.judgedAt = judgedAt;
    }

    public static Submission create(SubmissionId id, UserId userId, ProblemId problemId, SubmitSql submitSql) {
        return new Submission(
                id,
                userId,
                problemId,
                submitSql,
                SubmissionStatus.PENDING,
                JudgeScore.zero(),
                JudgeError.none(),
                LocalDateTime.now(),
                null
        );
    }

    public void markJudging() {
        if (status != SubmissionStatus.PENDING) {
            throw new DomainException("only pending submission can be marked judging");
        }
        this.status = SubmissionStatus.JUDGING;
    }

    public void applyJudgeResult(JudgeResult result) {
        Objects.requireNonNull(result, "result must not be null");
        switch (result.getStatus()) {
            case AC -> markAccepted(result.getScore());
            case WA -> markWrongAnswer(result.getScore(), result.getError());
            case RE -> markRuntimeError(result.getError());
            case TLE -> markTimeout(result.getError());
            case CE -> markCompileError(result.getError());
            case SE -> markSystemError(result.getError());
        }
        this.judgedAt = result.getJudgedAt();
    }

    public void markAccepted(JudgeScore score) {
        this.status = SubmissionStatus.AC;
        this.score = Objects.requireNonNull(score, "score must not be null");
        this.error = JudgeError.none();
    }

    public void markWrongAnswer(JudgeScore score, JudgeError error) {
        this.status = SubmissionStatus.WA;
        this.score = Objects.requireNonNull(score, "score must not be null");
        this.error = Objects.requireNonNull(error, "error must not be null");
    }

    public void markRuntimeError(JudgeError error) {
        markFailed(SubmissionStatus.RE, error);
    }

    public void markTimeout(JudgeError error) {
        markFailed(SubmissionStatus.TLE, error);
    }

    public void markCompileError(JudgeError error) {
        markFailed(SubmissionStatus.CE, error);
    }

    public void markSystemError(JudgeError error) {
        markFailed(SubmissionStatus.SE, error);
    }

    public SubmissionId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public ProblemId getProblemId() {
        return problemId;
    }

    public SubmitSql getSubmitSql() {
        return submitSql;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public JudgeScore getScore() {
        return score;
    }

    public JudgeError getError() {
        return error;
    }

    public LocalDateTime getJudgedAt() {
        return judgedAt;
    }

    private void markFailed(SubmissionStatus status, JudgeError error) {
        this.status = status;
        this.score = JudgeScore.zero();
        this.error = Objects.requireNonNull(error, "error must not be null");
    }
}

