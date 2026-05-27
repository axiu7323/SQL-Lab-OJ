package com.sqloj.domain.judge;

import com.sqloj.domain.shared.exception.DomainException;
import com.sqloj.domain.submission.SubmissionId;

import java.time.LocalDateTime;
import java.util.Objects;

public class JudgeResult {

    private final SubmissionId submissionId;
    private final JudgeStatus status;
    private final JudgeScore score;
    private final JudgeError error;
    private final long executeTimeMs;
    private final LocalDateTime judgedAt;

    public JudgeResult(
            SubmissionId submissionId,
            JudgeStatus status,
            JudgeScore score,
            JudgeError error,
            long executeTimeMs,
            LocalDateTime judgedAt
    ) {
        if (executeTimeMs < 0) {
            throw new DomainException("execute time must not be negative");
        }
        this.submissionId = Objects.requireNonNull(submissionId, "submissionId must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.score = Objects.requireNonNull(score, "score must not be null");
        this.error = Objects.requireNonNull(error, "error must not be null");
        this.executeTimeMs = executeTimeMs;
        this.judgedAt = Objects.requireNonNull(judgedAt, "judgedAt must not be null");
    }

    public static JudgeResult accepted(SubmissionId submissionId, long executeTimeMs) {
        return new JudgeResult(submissionId, JudgeStatus.AC, JudgeScore.full(), JudgeError.none(), executeTimeMs, LocalDateTime.now());
    }

    public static JudgeResult wrongAnswer(SubmissionId submissionId, JudgeError error, long executeTimeMs) {
        return new JudgeResult(submissionId, JudgeStatus.WA, JudgeScore.zero(), error, executeTimeMs, LocalDateTime.now());
    }

    public static JudgeResult runtimeError(SubmissionId submissionId, JudgeError error, long executeTimeMs) {
        return new JudgeResult(submissionId, JudgeStatus.RE, JudgeScore.zero(), error, executeTimeMs, LocalDateTime.now());
    }

    public static JudgeResult timeout(SubmissionId submissionId, long executeTimeMs) {
        return new JudgeResult(submissionId, JudgeStatus.TLE, JudgeScore.zero(), JudgeError.of("TLE", "execution timeout"), executeTimeMs, LocalDateTime.now());
    }

    public static JudgeResult compileError(SubmissionId submissionId, JudgeError error) {
        return new JudgeResult(submissionId, JudgeStatus.CE, JudgeScore.zero(), error, 0, LocalDateTime.now());
    }

    public static JudgeResult systemError(SubmissionId submissionId, JudgeError error) {
        return new JudgeResult(submissionId, JudgeStatus.SE, JudgeScore.zero(), error, 0, LocalDateTime.now());
    }

    public SubmissionId getSubmissionId() {
        return submissionId;
    }

    public JudgeStatus getStatus() {
        return status;
    }

    public JudgeScore getScore() {
        return score;
    }

    public JudgeError getError() {
        return error;
    }

    public long getExecuteTimeMs() {
        return executeTimeMs;
    }

    public LocalDateTime getJudgedAt() {
        return judgedAt;
    }
}

