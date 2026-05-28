package com.sqloj.domain.judge;

import com.sqloj.domain.submission.SubmissionId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JudgeDomainServiceTest {

    private final JudgeDomainService judgeDomainService = new JudgeDomainService();

    @Test
    void matchedResultShouldCreateAcceptedJudgeResult() {
        SqlExecutionResult expected = new SqlExecutionResult(List.of("id"), List.of(List.of(1)), 3);
        SqlExecutionResult actual = new SqlExecutionResult(List.of("id"), List.of(List.of(1)), 5);

        JudgeResult result = judgeDomainService.judgeSuccessOrWrongAnswer(
                SubmissionId.of(1L),
                expected,
                actual,
                new ResultCompareOptions(true, true)
        );

        assertEquals(JudgeStatus.AC, result.getStatus());
        assertEquals(100, result.getScore().value());
        assertEquals(8, result.getExecuteTimeMs());
    }
}
