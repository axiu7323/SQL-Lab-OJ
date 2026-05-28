package com.sqloj.application.submission;

import com.sqloj.application.submission.command.SubmitSqlCommand;
import com.sqloj.application.submission.dto.SubmissionDetailDTO;
import com.sqloj.application.submission.query.SubmissionQuery;
import com.sqloj.application.submission.service.SubmissionApplicationService;
import com.sqloj.common.exception.BusinessException;
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
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubmissionApplicationServiceTest {

    @Test
    void submitSqlShouldCreatePendingSubmission() {
        FakeProblemRepository problemRepository = new FakeProblemRepository();
        FakeSubmissionRepository submissionRepository = new FakeSubmissionRepository();
        SubmissionApplicationService service = new SubmissionApplicationService(submissionRepository, problemRepository);

        SubmissionDetailDTO detail = service.submitSql(new SubmitSqlCommand(1L, 1L, "SELECT id FROM student"));

        assertEquals(SubmissionStatus.PENDING, detail.status());
        assertEquals(1L, detail.userId());
        assertEquals(1L, submissionRepository.storage.size());
    }

    @Test
    void studentCanOnlyViewOwnSubmissionDetail() {
        FakeProblemRepository problemRepository = new FakeProblemRepository();
        FakeSubmissionRepository submissionRepository = new FakeSubmissionRepository();
        SubmissionApplicationService service = new SubmissionApplicationService(submissionRepository, problemRepository);
        SubmissionDetailDTO detail = service.submitSql(new SubmitSqlCommand(1L, 1L, "SELECT id FROM student"));

        assertThrows(BusinessException.class, () -> service.getMySubmissionDetail(2L, detail.id()));
    }

    @Test
    void teacherCanQuerySubmissionsByStudentAndProblem() {
        FakeProblemRepository problemRepository = new FakeProblemRepository();
        FakeSubmissionRepository submissionRepository = new FakeSubmissionRepository();
        SubmissionApplicationService service = new SubmissionApplicationService(submissionRepository, problemRepository);
        service.submitSql(new SubmitSqlCommand(1L, 1L, "SELECT id FROM student"));
        service.submitSql(new SubmitSqlCommand(2L, 1L, "SELECT id FROM student"));

        PageResult<?> page = service.pageTeacherSubmissions(new SubmissionQuery(
                1,
                10,
                1L,
                1L,
                SubmissionStatus.PENDING,
                null,
                null,
                true
        ));

        assertEquals(1, page.records().size());
    }

    private static class FakeProblemRepository implements ProblemRepository {

        private final Map<Long, Problem> storage = new LinkedHashMap<>();

        private FakeProblemRepository() {
            Problem problem = Problem.draft(
                    ProblemId.of(1L),
                    "SQL basic select",
                    "Query all student rows.",
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

        private final AtomicLong idGenerator = new AtomicLong(1);
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
            List<Submission> records = storage.values().stream()
                    .filter(submission -> criteria.studentId() == null || submission.getUserId().equals(criteria.studentId()))
                    .filter(submission -> criteria.problemId() == null || submission.getProblemId().equals(criteria.problemId()))
                    .filter(submission -> criteria.status() == null || submission.getStatus() == criteria.status())
                    .toList();
            return PageResult.of(records, records.size(), criteria.pageNo(), criteria.pageSize());
        }

        @Override
        public void save(Submission submission) {
            storage.put(submission.getId().value(), submission);
        }
    }
}

