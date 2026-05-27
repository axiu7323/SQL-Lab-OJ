package com.sqloj.application.problem.service;

import com.sqloj.application.problem.command.CreateProblemCommand;
import com.sqloj.application.problem.command.UpdateProblemCommand;
import com.sqloj.application.problem.dto.ProblemCaseDTO;
import com.sqloj.application.problem.dto.ProblemDTO;
import com.sqloj.application.problem.dto.ProblemDetailDTO;
import com.sqloj.application.problem.query.ProblemPageQuery;
import com.sqloj.common.enums.ErrorCode;
import com.sqloj.common.exception.BusinessException;
import com.sqloj.domain.problem.JudgeConfig;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemCase;
import com.sqloj.domain.problem.ProblemCategory;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemRepository;
import com.sqloj.domain.problem.ProblemStatus;
import com.sqloj.domain.problem.SqlScript;
import com.sqloj.domain.shared.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProblemApplicationService {

    private static final String DEFAULT_CASE_NAME = "default";

    private final ProblemRepository problemRepository;

    public ProblemApplicationService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProblemDetailDTO createProblem(CreateProblemCommand command) {
        Problem problem = Problem.draft(
                problemRepository.nextId(),
                command.title(),
                command.description(),
                toCategory(command.categoryId(), command.categoryName()),
                command.difficulty(),
                toJudgeConfig(command.timeLimitMs(), command.maxRows(), command.orderSensitive(), command.checkColumnName()),
                List.of(toProblemCase(null, command.initSchemaSql(), command.initDataSql(), command.standardSql()))
        );
        problem.updateScore(command.score());
        applyStatus(problem, command.status());
        problemRepository.save(problem);
        return toDetailDTO(problem, true);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProblemDetailDTO updateProblem(UpdateProblemCommand command) {
        Problem problem = findRequired(command.problemId());
        problem.updateBasicInfo(
                command.title(),
                command.description(),
                toCategory(command.categoryId(), command.categoryName()),
                command.difficulty()
        );
        problem.updateScore(command.score());
        problem.updateJudgeConfig(toJudgeConfig(
                command.timeLimitMs(),
                command.maxRows(),
                command.orderSensitive(),
                command.checkColumnName()
        ));
        problem.replaceCases(List.of(toProblemCase(null, command.initSchemaSql(), command.initDataSql(), command.standardSql())));
        applyStatus(problem, command.status());
        problemRepository.save(problem);
        return toDetailDTO(problem, true);
    }

    @Transactional(rollbackFor = Exception.class)
    public void enableProblem(Long problemId) {
        Problem problem = findRequired(problemId);
        problem.enable();
        problemRepository.save(problem);
    }

    @Transactional(rollbackFor = Exception.class)
    public void disableProblem(Long problemId) {
        Problem problem = findRequired(problemId);
        problem.disable();
        problemRepository.save(problem);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProblem(Long problemId) {
        Problem problem = findRequired(problemId);
        problem.delete();
        problemRepository.save(problem);
        problemRepository.deleteById(problem.getId());
    }

    @Transactional(readOnly = true)
    public ProblemDetailDTO getTeacherProblemDetail(Long problemId) {
        return toDetailDTO(findRequired(problemId), true);
    }

    @Transactional(readOnly = true)
    public ProblemDetailDTO getStudentProblemDetail(Long problemId) {
        Problem problem = findRequired(problemId);
        if (!problem.canSubmit()) {
            throw new BusinessException(ErrorCode.PROBLEM_NOT_ENABLED);
        }
        return toDetailDTO(problem, false);
    }

    @Transactional(readOnly = true)
    public PageResult<ProblemDTO> pageTeacherProblems(ProblemPageQuery query) {
        PageResult<Problem> page = problemRepository.page(query.toCriteria());
        return PageResult.of(page.records().stream().map(this::toDTO).toList(), page.total(), page.pageNo(), page.pageSize());
    }

    @Transactional(readOnly = true)
    public PageResult<ProblemDTO> pageStudentVisibleProblems(ProblemPageQuery query) {
        PageResult<Problem> page = problemRepository.page(query.toCriteria());
        return PageResult.of(page.records().stream().map(this::toDTO).toList(), page.total(), page.pageNo(), page.pageSize());
    }

    private Problem findRequired(Long problemId) {
        return problemRepository.findById(ProblemId.of(problemId))
                .orElseThrow(() -> new BusinessException(ErrorCode.PROBLEM_NOT_FOUND));
    }

    private ProblemCategory toCategory(Long categoryId, String categoryName) {
        String name = categoryName == null || categoryName.isBlank() ? "category-" + categoryId : categoryName;
        return new ProblemCategory(categoryId, name);
    }

    private JudgeConfig toJudgeConfig(int timeLimitMs, int maxRows, boolean orderSensitive, boolean checkColumnName) {
        return new JudgeConfig(timeLimitMs, maxRows, orderSensitive, checkColumnName);
    }

    private ProblemCase toProblemCase(Long id, String schemaSql, String initDataSql, String standardSql) {
        return new ProblemCase(
                id,
                DEFAULT_CASE_NAME,
                SqlScript.of(schemaSql),
                SqlScript.of(initDataSql),
                SqlScript.of(standardSql),
                false
        );
    }

    private void applyStatus(Problem problem, ProblemStatus status) {
        ProblemStatus targetStatus = status == null ? ProblemStatus.DRAFT : status;
        if (targetStatus == ProblemStatus.ENABLED) {
            problem.enable();
        } else if (targetStatus == ProblemStatus.DISABLED) {
            problem.disable();
        }
    }

    private ProblemDTO toDTO(Problem problem) {
        JudgeConfig judgeConfig = problem.getJudgeConfig();
        return new ProblemDTO(
                problem.getId().value(),
                problem.getTitle(),
                problem.getCategory().id(),
                problem.getCategory().name(),
                problem.getDifficulty(),
                problem.getStatus(),
                problem.getScore(),
                judgeConfig.orderSensitive(),
                judgeConfig.checkColumnName(),
                judgeConfig.timeLimitMs(),
                judgeConfig.maxRows(),
                null,
                null
        );
    }

    private ProblemDetailDTO toDetailDTO(Problem problem, boolean includeExpectedSql) {
        JudgeConfig judgeConfig = problem.getJudgeConfig();
        return new ProblemDetailDTO(
                problem.getId().value(),
                problem.getTitle(),
                problem.getDescription(),
                problem.getCategory().id(),
                problem.getCategory().name(),
                problem.getDifficulty(),
                problem.getStatus(),
                problem.getScore(),
                judgeConfig.orderSensitive(),
                judgeConfig.checkColumnName(),
                judgeConfig.timeLimitMs(),
                judgeConfig.maxRows(),
                problem.getCases().stream()
                        .map(problemCase -> toCaseDTO(problemCase, includeExpectedSql))
                        .toList()
        );
    }

    private ProblemCaseDTO toCaseDTO(ProblemCase problemCase, boolean includeExpectedSql) {
        return new ProblemCaseDTO(
                problemCase.id(),
                problemCase.name(),
                problemCase.schemaSql().content(),
                problemCase.initDataSql().content(),
                includeExpectedSql ? problemCase.expectedSql().content() : null,
                problemCase.sampleCase()
        );
    }
}

