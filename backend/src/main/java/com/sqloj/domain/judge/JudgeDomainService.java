package com.sqloj.domain.judge;

import com.sqloj.domain.submission.SubmissionId;

public class JudgeDomainService {

    private final SqlSafetyChecker sqlSafetyChecker;
    private final ResultSetComparator resultSetComparator;
    private final ScoreCalculator scoreCalculator;

    public JudgeDomainService() {
        this(new SqlSafetyChecker(), new ResultSetComparator(), new ScoreCalculator());
    }

    public JudgeDomainService(
            SqlSafetyChecker sqlSafetyChecker,
            ResultSetComparator resultSetComparator,
            ScoreCalculator scoreCalculator
    ) {
        this.sqlSafetyChecker = sqlSafetyChecker;
        this.resultSetComparator = resultSetComparator;
        this.scoreCalculator = scoreCalculator;
    }

    public SqlSafetyCheckResult checkSql(String sql) {
        return sqlSafetyChecker.check(sql);
    }

    public JudgeResult judgeSuccessOrWrongAnswer(
            SubmissionId submissionId,
            SqlExecutionResult expectedResult,
            SqlExecutionResult actualResult,
            ResultCompareOptions options
    ) {
        ResultSetDiff diff = resultSetComparator.compare(expectedResult, actualResult, options);
        JudgeScore score = scoreCalculator.calculate(diff);
        long executeTimeMs = expectedResult.executeTimeMs() + actualResult.executeTimeMs();
        if (diff.matched()) {
            return new JudgeResult(submissionId, JudgeStatus.AC, score, JudgeError.none(), executeTimeMs, java.time.LocalDateTime.now());
        }
        return new JudgeResult(
                submissionId,
                JudgeStatus.WA,
                score,
                JudgeError.of("WA", diff.message(), "row=" + diff.rowIndex() + ", column=" + diff.columnIndex()),
                executeTimeMs,
                java.time.LocalDateTime.now()
        );
    }
}

