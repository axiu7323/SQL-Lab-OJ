package com.sqloj.interfaces.controller;

import com.sqloj.application.submission.service.SubmissionApplicationService;
import com.sqloj.common.result.ApiResponse;
import com.sqloj.common.result.PageResponse;
import com.sqloj.interfaces.assembler.SubmissionAssembler;
import com.sqloj.interfaces.request.SubmissionPageRequest;
import com.sqloj.interfaces.request.SubmitSqlRequest;
import com.sqloj.interfaces.response.SubmissionDetailResponse;
import com.sqloj.interfaces.response.SubmissionResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmissionController {

    private final SubmissionApplicationService submissionApplicationService;
    private final SubmissionAssembler submissionAssembler;

    public SubmissionController(
            SubmissionApplicationService submissionApplicationService,
            SubmissionAssembler submissionAssembler
    ) {
        this.submissionApplicationService = submissionApplicationService;
        this.submissionAssembler = submissionAssembler;
    }

    @PostMapping("/api/submissions")
    public ApiResponse<SubmissionDetailResponse> submitSql(@Valid @RequestBody SubmitSqlRequest request) {
        return ApiResponse.success(submissionAssembler.toDetailResponse(
                submissionApplicationService.submitSql(submissionAssembler.toCommand(request))
        ));
    }

    @GetMapping("/api/submissions/my")
    public ApiResponse<PageResponse<SubmissionResponse>> pageMySubmissions(SubmissionPageRequest request) {
        return ApiResponse.success(submissionAssembler.toPageResponse(
                submissionApplicationService.pageMySubmissions(submissionAssembler.toStudentQuery(request))
        ));
    }

    @GetMapping("/api/submissions/{submissionId}")
    public ApiResponse<SubmissionDetailResponse> getMySubmissionDetail(
            @PathVariable Long submissionId,
            @RequestParam Long userId
    ) {
        return ApiResponse.success(submissionAssembler.toDetailResponse(
                submissionApplicationService.getMySubmissionDetail(userId, submissionId)
        ));
    }

    @GetMapping("/api/teacher/submissions")
    public ApiResponse<PageResponse<SubmissionResponse>> pageTeacherSubmissions(SubmissionPageRequest request) {
        return ApiResponse.success(submissionAssembler.toPageResponse(
                submissionApplicationService.pageTeacherSubmissions(submissionAssembler.toTeacherQuery(request))
        ));
    }

    @GetMapping("/api/teacher/submissions/{submissionId}")
    public ApiResponse<SubmissionDetailResponse> getTeacherSubmissionDetail(@PathVariable Long submissionId) {
        return ApiResponse.success(submissionAssembler.toDetailResponse(
                submissionApplicationService.getTeacherSubmissionDetail(submissionId)
        ));
    }
}

