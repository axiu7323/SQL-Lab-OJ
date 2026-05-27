package com.sqloj.interfaces.assembler;

import com.sqloj.application.problem.command.CreateProblemCommand;
import com.sqloj.application.problem.command.UpdateProblemCommand;
import com.sqloj.application.problem.dto.ProblemCaseDTO;
import com.sqloj.application.problem.dto.ProblemDTO;
import com.sqloj.application.problem.dto.ProblemDetailDTO;
import com.sqloj.application.problem.query.ProblemPageQuery;
import com.sqloj.common.result.PageResponse;
import com.sqloj.domain.problem.JudgeConfig;
import com.sqloj.interfaces.request.ProblemPageRequest;
import com.sqloj.interfaces.request.TeacherProblemRequest;
import com.sqloj.interfaces.response.ProblemCaseResponse;
import com.sqloj.interfaces.response.ProblemDetailResponse;
import com.sqloj.interfaces.response.ProblemResponse;
import org.springframework.stereotype.Component;

@Component
public class ProblemAssembler {

    public CreateProblemCommand toCreateCommand(TeacherProblemRequest request) {
        return new CreateProblemCommand(
                request.title(),
                request.description(),
                request.categoryId(),
                request.categoryName(),
                request.difficulty(),
                defaultScore(request.score()),
                request.initSchemaSql(),
                request.initDataSql(),
                request.standardSql(),
                defaultBoolean(request.orderSensitive(), false),
                defaultBoolean(request.checkColumnName(), true),
                defaultInt(request.timeLimitMs(), JudgeConfig.DEFAULT_TIME_LIMIT_MS),
                defaultInt(request.maxRows(), JudgeConfig.DEFAULT_MAX_ROWS),
                request.status()
        );
    }

    public UpdateProblemCommand toUpdateCommand(Long problemId, TeacherProblemRequest request) {
        return new UpdateProblemCommand(
                problemId,
                request.title(),
                request.description(),
                request.categoryId(),
                request.categoryName(),
                request.difficulty(),
                defaultScore(request.score()),
                request.initSchemaSql(),
                request.initDataSql(),
                request.standardSql(),
                defaultBoolean(request.orderSensitive(), false),
                defaultBoolean(request.checkColumnName(), true),
                defaultInt(request.timeLimitMs(), JudgeConfig.DEFAULT_TIME_LIMIT_MS),
                defaultInt(request.maxRows(), JudgeConfig.DEFAULT_MAX_ROWS),
                request.status()
        );
    }

    public ProblemPageQuery toTeacherQuery(ProblemPageRequest request) {
        return new ProblemPageQuery(
                defaultLong(request.getPageNo(), 1L),
                defaultLong(request.getPageSize(), 10L),
                request.getKeyword(),
                request.getCategoryId(),
                request.getDifficulty(),
                request.getStatus(),
                false
        );
    }

    public ProblemPageQuery toStudentQuery(ProblemPageRequest request) {
        return new ProblemPageQuery(
                defaultLong(request.getPageNo(), 1L),
                defaultLong(request.getPageSize(), 10L),
                request.getKeyword(),
                request.getCategoryId(),
                request.getDifficulty(),
                null,
                true
        );
    }

    public PageResponse<ProblemResponse> toPageResponse(com.sqloj.domain.shared.PageResult<ProblemDTO> page) {
        return PageResponse.of(
                page.records().stream().map(this::toResponse).toList(),
                page.total(),
                page.pageNo(),
                page.pageSize()
        );
    }

    public ProblemResponse toResponse(ProblemDTO dto) {
        return new ProblemResponse(
                dto.id(),
                dto.title(),
                dto.categoryId(),
                dto.categoryName(),
                dto.difficulty(),
                dto.status(),
                dto.score(),
                dto.orderSensitive(),
                dto.checkColumnName(),
                dto.timeLimitMs(),
                dto.maxRows(),
                dto.passRate(),
                dto.myStatus()
        );
    }

    public ProblemDetailResponse toDetailResponse(ProblemDetailDTO dto) {
        return new ProblemDetailResponse(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.categoryId(),
                dto.categoryName(),
                dto.difficulty(),
                dto.status(),
                dto.score(),
                dto.orderSensitive(),
                dto.checkColumnName(),
                dto.timeLimitMs(),
                dto.maxRows(),
                dto.cases().stream().map(this::toCaseResponse).toList()
        );
    }

    private ProblemCaseResponse toCaseResponse(ProblemCaseDTO dto) {
        return new ProblemCaseResponse(
                dto.id(),
                dto.name(),
                dto.schemaSql(),
                dto.initDataSql(),
                dto.standardSql(),
                dto.sampleCase()
        );
    }

    private int defaultScore(Integer score) {
        return score == null ? 100 : score;
    }

    private boolean defaultBoolean(Boolean value, boolean defaultValue) {
        return value == null ? defaultValue : value;
    }

    private int defaultInt(Integer value, int defaultValue) {
        return value == null ? defaultValue : value;
    }

    private long defaultLong(Long value, long defaultValue) {
        return value == null ? defaultValue : value;
    }
}

