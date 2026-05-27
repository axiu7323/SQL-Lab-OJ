package com.sqloj.interfaces.controller;

import com.sqloj.application.problem.service.ProblemApplicationService;
import com.sqloj.common.result.ApiResponse;
import com.sqloj.common.result.PageResponse;
import com.sqloj.interfaces.assembler.ProblemAssembler;
import com.sqloj.interfaces.request.ProblemPageRequest;
import com.sqloj.interfaces.request.TeacherProblemRequest;
import com.sqloj.interfaces.response.ProblemDetailResponse;
import com.sqloj.interfaces.response.ProblemResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {

    private final ProblemApplicationService problemApplicationService;
    private final ProblemAssembler problemAssembler;

    public ProblemController(ProblemApplicationService problemApplicationService, ProblemAssembler problemAssembler) {
        this.problemApplicationService = problemApplicationService;
        this.problemAssembler = problemAssembler;
    }

    @GetMapping("/api/problems")
    public ApiResponse<PageResponse<ProblemResponse>> pageStudentProblems(ProblemPageRequest request) {
        return ApiResponse.success(problemAssembler.toPageResponse(
                problemApplicationService.pageStudentVisibleProblems(problemAssembler.toStudentQuery(request))
        ));
    }

    @GetMapping("/api/problems/{problemId}")
    public ApiResponse<ProblemDetailResponse> getStudentProblemDetail(@PathVariable Long problemId) {
        return ApiResponse.success(problemAssembler.toDetailResponse(
                problemApplicationService.getStudentProblemDetail(problemId)
        ));
    }

    @GetMapping("/api/teacher/problems")
    public ApiResponse<PageResponse<ProblemResponse>> pageTeacherProblems(ProblemPageRequest request) {
        return ApiResponse.success(problemAssembler.toPageResponse(
                problemApplicationService.pageTeacherProblems(problemAssembler.toTeacherQuery(request))
        ));
    }

    @GetMapping("/api/teacher/problems/{problemId}")
    public ApiResponse<ProblemDetailResponse> getTeacherProblemDetail(@PathVariable Long problemId) {
        return ApiResponse.success(problemAssembler.toDetailResponse(
                problemApplicationService.getTeacherProblemDetail(problemId)
        ));
    }

    @PostMapping("/api/teacher/problems")
    public ApiResponse<ProblemDetailResponse> createProblem(@Valid @RequestBody TeacherProblemRequest request) {
        return ApiResponse.success(problemAssembler.toDetailResponse(
                problemApplicationService.createProblem(problemAssembler.toCreateCommand(request))
        ));
    }

    @PutMapping("/api/teacher/problems/{problemId}")
    public ApiResponse<ProblemDetailResponse> updateProblem(
            @PathVariable Long problemId,
            @Valid @RequestBody TeacherProblemRequest request
    ) {
        return ApiResponse.success(problemAssembler.toDetailResponse(
                problemApplicationService.updateProblem(problemAssembler.toUpdateCommand(problemId, request))
        ));
    }

    @PostMapping("/api/teacher/problems/{problemId}/enable")
    public ApiResponse<Void> enableProblem(@PathVariable Long problemId) {
        problemApplicationService.enableProblem(problemId);
        return ApiResponse.success();
    }

    @PostMapping("/api/teacher/problems/{problemId}/disable")
    public ApiResponse<Void> disableProblem(@PathVariable Long problemId) {
        problemApplicationService.disableProblem(problemId);
        return ApiResponse.success();
    }

    @DeleteMapping("/api/teacher/problems/{problemId}")
    public ApiResponse<Void> deleteProblem(@PathVariable Long problemId) {
        problemApplicationService.deleteProblem(problemId);
        return ApiResponse.success();
    }
}

