package com.sqloj.application.judge;

import com.sqloj.application.judge.command.JudgeTaskCommand;
import com.sqloj.application.judge.dto.JudgeResultDTO;
import com.sqloj.application.judge.port.SqlSandbox;
import com.sqloj.application.judge.port.SqlSandboxCallback;
import com.sqloj.application.judge.port.SqlSandboxManager;
import com.sqloj.application.judge.service.JudgeApplicationService;
import com.sqloj.domain.judge.JudgeDomainService;
import com.sqloj.domain.judge.JudgeResult;
import com.sqloj.domain.judge.JudgeResultRepository;
import com.sqloj.domain.judge.JudgeStatus;
import com.sqloj.domain.judge.SqlExecutionResult;
import com.sqloj.domain.problem.JudgeConfig;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemCase;
import com.sqloj.domain.problem.ProblemCategory;
import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemPageCriteria;
import com.sqloj.domain.problem.ProblemRepository;
import com.sqloj.domain.problem.SqlScript;
import com.sqloj.domain.shared.PageResult;
import com.sqloj.domain.submission.Submission;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmissionPageCriteria;
import com.sqloj.domain.submission.SubmissionRepository;
import com.sqloj.domain.submission.SubmissionStatus;
import com.sqloj.domain.submission.SubmitSql;
import com.sqloj.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JudgeApplicationServiceTest {

    @Test
    void judgeShouldAcceptCorrectSubmissionAndUpdateSubmission() {
        FakeSubmissionRepository submissionRepository = new FakeSubmissionRepository();
        FakeProblemRepository problemRepository = new FakeProblemRepository();
        FakeJudgeResultRepository judgeResultRepository = new FakeJudgeResultRepository();
        JudgeApplicationService service = new JudgeApplicationService(
                submissionRepository,
                problemRepository,
                judgeResultRepository,
                new JudgeDomainService(),
                new FakeSqlSandboxManager()
        );
        Submission submission = Submission.create(
                SubmissionId.of(1L),
                UserId.of(1L),
                ProblemId.of(1L),
                SubmitSql.of("SELECT id FROM student")
        );
        submissionRepository.save(submission);

        JudgeResultDTO result = service.judge(new JudgeTaskCommand(1L));

        assertEquals(JudgeStatus.AC, result.status());
        assertEquals(SubmissionStatus.AC, submissionRepository.storage.get(1L).getStatus());
        assertEquals(1, judgeResultRepository.storage.size());
    }

    @Test
    void unsafeSqlShouldCreateCompileError() {
        FakeSubmissionRepository submissionRepository = new FakeSubmissionRepository();
        FakeProblemRepository problemRepository = new FakeProblemRepository();
        FakeJudgeResultRepository judgeResultRepository = new FakeJudgeResultRepository();
        JudgeApplicationService service = new JudgeApplicationService(
                submissionRepository,
                problemRepository,
                judgeResultRepository,
                new JudgeDomainService(),
                new FakeSqlSandboxManager()
        );
        Submission submission = Submission.create(
                SubmissionId.of(2L),
                UserId.of(1L),
                ProblemId.of(1L),
                SubmitSql.of("SELECT id FROM student; DROP TABLE student")
        );
        submissionRepository.save(submission);

        JudgeResultDTO result = service.judge(new JudgeTaskCommand(2L));

        assertEquals(JudgeStatus.CE, result.status());
        assertEquals(SubmissionStatus.CE, submissionRepository.storage.get(2L).getStatus());
    }

    private static class FakeProblemRepository implements ProblemRepository {

        private final Map<Long, Problem> storage = new LinkedHashMap<>();

        private FakeProblemRepository() {
            Problem problem = Problem.draft(
                    ProblemId.of(1L),
                    "SQL basic select",
                    "Query student ids.",
                    new ProblemCategory(1L, "basic"),
                    ProblemDifficulty.EASY,
                    JudgeConfig.defaultConfig(),
                    List.of(new ProblemCase(
                            1L,
                            "default",
                            SqlScript.of("CREATE TABLE student(id BIGINT)"),
                            SqlScript.of("INSERT INTO student(id) VALUES (1)"),
                            SqlScript.of("SELECT id FROM student"),
                            false
                    ))
            );
            problem.enable();
            storage.put(1L, problem);
        }

        @Override
        public ProblemId nextId() {
            return ProblemId.of(2L);
        }

        @Override
        public Optional<Problem> findById(ProblemId id) {
            return Optional.ofNullable(storage.get(id.value()));
        }

        @Override
        public PageResult<Problem> page(ProblemPageCriteria criteria) {
            return PageResult.of(List.copyOf(storage.values()), storage.size(), criteria.pageNo(), criteria.pageSize());
        }

        @Override
        public void save(Problem problem) {
            storage.put(problem.getId().value(), problem);
        }

        @Override
        public void deleteById(ProblemId id) {
            storage.remove(id.value());
        }
    }

    private static class FakeSubmissionRepository implements SubmissionRepository {

        private final AtomicLong idGenerator = new AtomicLong(10);
        private final Map<Long, Submission> storage = new LinkedHashMap<>();

        @Override
        public SubmissionId nextId() {
            return SubmissionId.of(idGenerator.getAndIncrement());
        }

        @Override
        public Optional<Submission> findById(SubmissionId id) {
            return Optional.ofNullable(storage.get(id.value()));
        }

        @Override
        public PageResult<Submission> page(SubmissionPageCriteria criteria) {
            return PageResult.of(List.copyOf(storage.values()), storage.size(), criteria.pageNo(), criteria.pageSize());
        }

        @Override
        public void save(Submission submission) {
            storage.put(submission.getId().value(), submission);
        }
    }

    private static class FakeJudgeResultRepository implements JudgeResultRepository {

        private final Map<Long, JudgeResult> storage = new LinkedHashMap<>();

        @Override
        public Optional<JudgeResult> findBySubmissionId(SubmissionId submissionId) {
            return Optional.ofNullable(storage.get(submissionId.value()));
        }

        @Override
        public void save(JudgeResult result) {
            storage.put(result.getSubmissionId().value(), result);
        }
    }

    private static class FakeSqlSandboxManager implements SqlSandboxManager {

        @Override
        public <T> T executeInSandbox(Long submissionId, SqlSandboxCallback<T> callback) {
            return callback.execute(new FakeSqlSandbox());
        }
    }

    private static class FakeSqlSandbox implements SqlSandbox {

        @Override
        public void executeStatement(String sql) {
        }

        @Override
        public SqlExecutionResult executeQuery(String sql, int maxRows, int timeoutMs) {
            return new SqlExecutionResult(List.of("id"), List.of(List.of(1L)), 1);
        }
    }
}
