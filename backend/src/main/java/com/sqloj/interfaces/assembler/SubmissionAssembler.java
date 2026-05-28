package com.sqloj.interfaces.assembler;

import com.sqloj.application.submission.command.SubmitSqlCommand;
import com.sqloj.application.submission.dto.SubmissionDTO;
import com.sqloj.application.submission.dto.SubmissionDetailDTO;
import com.sqloj.application.submission.query.SubmissionQuery;
import com.sqloj.common.result.PageResponse;
import com.sqloj.domain.shared.PageResult;
import com.sqloj.interfaces.request.SubmissionPageRequest;
import com.sqloj.interfaces.request.SubmitSqlRequest;
import com.sqloj.interfaces.response.SubmissionDetailResponse;
import com.sqloj.interfaces.response.SubmissionResponse;
import org.springframework.stereotype.Component;

@Component
public class SubmissionAssembler {

    public SubmitSqlCommand toCommand(SubmitSqlRequest request) {
        return new SubmitSqlCommand(request.userId(), request.problemId(), request.submitSql());
    }

    public SubmissionQuery toStudentQuery(SubmissionPageRequest request) {
        return new SubmissionQuery(
                defaultLong(request.getPageNo(), 1L),
                defaultLong(request.getPageSize(), 10L),
                request.getUserId(),
                request.getProblemId(),
                request.getStatus(),
                null,
                null,
                false
        );
    }

    public SubmissionQuery toTeacherQuery(SubmissionPageRequest request) {
        return new SubmissionQuery(
                defaultLong(request.getPageNo(), 1L),
                defaultLong(request.getPageSize(), 10L),
                request.getStudentId(),
                request.getProblemId(),
                request.getStatus(),
                request.getStartTime(),
                request.getEndTime(),
                true
        );
    }

    public PageResponse<SubmissionResponse> toPageResponse(PageResult<SubmissionDTO> page) {
        return PageResponse.of(
                page.records().stream().map(this::toResponse).toList(),
                page.total(),
                page.pageNo(),
                page.pageSize()
        );
    }

    public SubmissionResponse toResponse(SubmissionDTO dto) {
        return new SubmissionResponse(
                dto.id(),
                dto.userId(),
                dto.problemId(),
                dto.status(),
                dto.score(),
                dto.message(),
                dto.executeTimeMs(),
                dto.submittedAt(),
                dto.judgedAt()
        );
    }

    public SubmissionDetailResponse toDetailResponse(SubmissionDetailDTO dto) {
        return new SubmissionDetailResponse(
                dto.id(),
                dto.userId(),
                dto.problemId(),
                dto.submitSql(),
                dto.status(),
                dto.score(),
                dto.message(),
                dto.executeTimeMs(),
                dto.submittedAt(),
                dto.judgedAt()
        );
    }

    private long defaultLong(Long value, long defaultValue) {
        return value == null ? defaultValue : value;
    }
}

