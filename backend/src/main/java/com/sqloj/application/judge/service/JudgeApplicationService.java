package com.sqloj.application.judge.service;

import com.sqloj.application.judge.command.JudgeTaskCommand;
import com.sqloj.application.judge.dto.JudgeResultDTO;
import com.sqloj.application.judge.port.SqlExecutionException;
import com.sqloj.application.judge.port.SqlExecutionFailureType;
import com.sqloj.application.judge.port.SqlSandboxManager;
import com.sqloj.common.enums.ErrorCode;
import com.sqloj.common.exception.BusinessException;
import com.sqloj.domain.judge.JudgeDomainService;
import com.sqloj.domain.judge.JudgeError;
import com.sqloj.domain.judge.JudgeResult;
import com.sqloj.domain.judge.JudgeResultRepository;
import com.sqloj.domain.judge.JudgeTask;
import com.sqloj.domain.judge.ResultCompareOptions;
import com.sqloj.domain.judge.SqlExecutionResult;
import com.sqloj.domain.judge.SqlSafetyCheckResult;
import com.sqloj.domain.problem.JudgeConfig;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemCase;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemRepository;
import com.sqloj.domain.submission.Submission;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JudgeApplicationService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final JudgeResultRepository judgeResultRepository;
    private final JudgeDomainService judgeDomainService;
    private final SqlSandboxManager sqlSandboxManager;

    public JudgeApplicationService(
            SubmissionRepository submissionRepository,
            ProblemRepository problemRepository,
            JudgeResultRepository judgeResultRepository,
            JudgeDomainService judgeDomainService,
            SqlSandboxManager sqlSandboxManager
    ) {
        this.submissionRepository = submissionRepository;
        this.problemRepository = problemRepository;
        this.judgeResultRepository = judgeResultRepository;
        this.judgeDomainService = judgeDomainService;
        this.sqlSandboxManager = sqlSandboxManager;
    }

    @Transactional(rollbackFor = Exception.class)
    public JudgeResultDTO judge(JudgeTaskCommand command) {
        Submission submission = findSubmission(command.submissionId());
        Problem problem = findProblem(submission);
        if (!problem.canSubmit()) {
            throw new BusinessException(ErrorCode.PROBLEM_NOT_ENABLED);
        }

        markJudgingIfNeeded(submission);
        JudgeTask task = JudgeTask.create(submission.getId(), problem.getId(), submission.getSubmitSql());
        SqlSafetyCheckResult safetyCheckResult = judgeDomainService.checkSql(task.getSubmitSql().value());
        if (!safetyCheckResult.safe()) {
            return finishJudge(submission, JudgeResult.compileError(
                    submission.getId(),
                    JudgeError.of("CE", safetyCheckResult.message())
            ));
        }

        try {
            JudgeResult judgeResult = executeInSandbox(problem, submission, safetyCheckResult.normalizedSql());
            return finishJudge(submission, judgeResult);
        } catch (SqlExecutionException ex) {
            return finishJudge(submission, toJudgeResult(submission.getId(), ex));
        } catch (RuntimeException ex) {
            return finishJudge(submission, JudgeResult.systemError(
                    submission.getId(),
                    JudgeError.of("SE", "judge system error", ex.getMessage())
            ));
        }
    }

    private Submission findSubmission(Long submissionId) {
        return submissionRepository.findById(SubmissionId.of(submissionId))
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBMISSION_NOT_FOUND));
    }

    private Problem findProblem(Submission submission) {
        return problemRepository.findById(submission.getProblemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROBLEM_NOT_FOUND));
    }

    private void markJudgingIfNeeded(Submission submission) {
        switch (submission.getStatus()) {
            case PENDING -> {
                submission.markJudging();
                submissionRepository.save(submission);
            }
            case JUDGING, AC, WA, RE, TLE, CE, SE -> {
                // Manual rejudge keeps the old result until the new result is persisted.
            }
        }
    }

    private JudgeResult executeInSandbox(Problem problem, Submission submission, String normalizedSql) {
        ProblemCase problemCase = problem.getCases().get(0);
        JudgeConfig judgeConfig = problem.getJudgeConfig();
        return sqlSandboxManager.executeInSandbox(submission.getId().value(), sandbox -> {
            sandbox.executeStatement(problemCase.schemaSql().content());
            sandbox.executeStatement(problemCase.initDataSql().content());
            SqlExecutionResult expected = sandbox.executeQuery(
                    problemCase.expectedSql().content(),
                    judgeConfig.maxRows(),
                    judgeConfig.timeLimitMs()
            );
            SqlExecutionResult actual = sandbox.executeQuery(
                    normalizedSql,
                    judgeConfig.maxRows(),
                    judgeConfig.timeLimitMs()
            );
            return judgeDomainService.judgeSuccessOrWrongAnswer(
                    submission.getId(),
                    expected,
                    actual,
                    new ResultCompareOptions(judgeConfig.orderSensitive(), judgeConfig.checkColumnName())
            );
        });
    }

    private JudgeResult toJudgeResult(SubmissionId submissionId, SqlExecutionException ex) {
        if (ex.getFailureType() == SqlExecutionFailureType.TIMEOUT) {
            return JudgeResult.timeout(submissionId, 0);
        }
        if (ex.getFailureType() == SqlExecutionFailureType.BAD_SQL) {
            return JudgeResult.compileError(submissionId, JudgeError.of("CE", ex.getMessage()));
        }
        if (ex.getFailureType() == SqlExecutionFailureType.RUNTIME_ERROR) {
            return JudgeResult.runtimeError(submissionId, JudgeError.of("RE", ex.getMessage()), 0);
        }
        return JudgeResult.systemError(submissionId, JudgeError.of("SE", ex.getMessage()));
    }

    private JudgeResultDTO finishJudge(Submission submission, JudgeResult result) {
        judgeResultRepository.save(result);
        submission.applyJudgeResult(result);
        submissionRepository.save(submission);
        return toDTO(result);
    }

    private JudgeResultDTO toDTO(JudgeResult result) {
        return new JudgeResultDTO(
                result.getSubmissionId().value(),
                result.getStatus(),
                result.getScore().value(),
                result.getError().message(),
                result.getError().detail(),
                result.getExecuteTimeMs(),
                result.getJudgedAt()
        );
    }
}
