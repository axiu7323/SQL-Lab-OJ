package com.sqloj.domain;

import com.sqloj.domain.judge.JudgeResult;
import com.sqloj.domain.problem.JudgeConfig;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemCase;
import com.sqloj.domain.problem.ProblemCategory;
import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.SqlScript;
import com.sqloj.domain.submission.Submission;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmissionStatus;
import com.sqloj.domain.submission.SubmitSql;
import com.sqloj.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainModelTests {

    @Test
    void problemCanBeEnabledAfterPublishValidation() {
        Problem problem = Problem.draft(
                ProblemId.of(1L),
                "query users",
                "query all users",
                new ProblemCategory(1L, "basic select"),
                ProblemDifficulty.EASY,
                JudgeConfig.defaultConfig(),
                List.of(new ProblemCase(
                        1L,
                        "default",
                        SqlScript.of("CREATE TABLE users(id BIGINT)"),
                        SqlScript.of("INSERT INTO users(id) VALUES (1)"),
                        SqlScript.of("SELECT id FROM users"),
                        false
                ))
        );

        problem.enable();

        assertTrue(problem.canSubmit());
    }

    @Test
    void submissionCanApplyAcceptedJudgeResult() {
        Submission submission = Submission.create(
                SubmissionId.of(1L),
                UserId.of(1L),
                ProblemId.of(1L),
                SubmitSql.of("SELECT 1")
        );

        submission.markJudging();
        submission.applyJudgeResult(JudgeResult.accepted(submission.getId(), 12));

        assertEquals(SubmissionStatus.AC, submission.getStatus());
        assertEquals(100, submission.getScore().value());
    }
}

