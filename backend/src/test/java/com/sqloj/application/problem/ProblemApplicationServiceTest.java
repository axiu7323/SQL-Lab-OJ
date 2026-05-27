package com.sqloj.application.problem;

import com.sqloj.application.problem.command.CreateProblemCommand;
import com.sqloj.application.problem.dto.ProblemDetailDTO;
import com.sqloj.application.problem.query.ProblemPageQuery;
import com.sqloj.application.problem.service.ProblemApplicationService;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemPageCriteria;
import com.sqloj.domain.problem.ProblemRepository;
import com.sqloj.domain.problem.ProblemStatus;
import com.sqloj.domain.shared.PageResult;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProblemApplicationServiceTest {

    @Test
    void createProblemShouldSaveDraftProblem() {
        FakeProblemRepository repository = new FakeProblemRepository();
        ProblemApplicationService service = new ProblemApplicationService(repository);

        ProblemDetailDTO detail = service.createProblem(createCommand(ProblemStatus.DRAFT));

        assertEquals("SQL basic select", detail.title());
        assertEquals(ProblemStatus.DRAFT, detail.status());
        assertEquals(1, repository.storage.size());
    }

    @Test
    void studentDetailShouldHideStandardSql() {
        FakeProblemRepository repository = new FakeProblemRepository();
        ProblemApplicationService service = new ProblemApplicationService(repository);
        ProblemDetailDTO created = service.createProblem(createCommand(ProblemStatus.ENABLED));

        ProblemDetailDTO studentDetail = service.getStudentProblemDetail(created.id());

        assertTrue(studentDetail.cases().stream().allMatch(problemCase -> problemCase.standardSql() == null));
    }

    @Test
    void deleteProblemShouldRemoveProblemFromRepository() {
        FakeProblemRepository repository = new FakeProblemRepository();
        ProblemApplicationService service = new ProblemApplicationService(repository);
        ProblemDetailDTO created = service.createProblem(createCommand(ProblemStatus.ENABLED));

        service.deleteProblem(created.id());

        assertNull(repository.storage.get(created.id()));
    }

    private CreateProblemCommand createCommand(ProblemStatus status) {
        return new CreateProblemCommand(
                "SQL basic select",
                "Query all student rows.",
                1L,
                "basic",
                ProblemDifficulty.EASY,
                100,
                "CREATE TABLE student(id BIGINT)",
                "INSERT INTO student(id) VALUES (1)",
                "SELECT id FROM student",
                false,
                true,
                3000,
                1000,
                status
        );
    }

    private static class FakeProblemRepository implements ProblemRepository {

        private final AtomicLong idGenerator = new AtomicLong(1);
        private final Map<Long, Problem> storage = new LinkedHashMap<>();

        @Override
        public ProblemId nextId() {
            return ProblemId.of(idGenerator.getAndIncrement());
        }

        @Override
        public Optional<Problem> findById(ProblemId id) {
            return Optional.ofNullable(storage.get(id.value()));
        }

        @Override
        public PageResult<Problem> page(ProblemPageCriteria criteria) {
            return PageResult.of(
                    storage.values().stream()
                            .filter(problem -> !criteria.enabledOnly() || problem.canSubmit())
                            .toList(),
                    storage.size(),
                    criteria.pageNo(),
                    criteria.pageSize()
            );
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
}

