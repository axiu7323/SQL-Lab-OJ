package com.sqloj.interfaces.controller;

import com.sqloj.application.judge.service.SubmissionJudgeService;
import com.sqloj.common.result.ApiResponse;
import com.sqloj.interfaces.assembler.JudgeAssembler;
import com.sqloj.interfaces.response.JudgeResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JudgeController {

    private final SubmissionJudgeService submissionJudgeService;
    private final JudgeAssembler judgeAssembler;

    public JudgeController(SubmissionJudgeService submissionJudgeService, JudgeAssembler judgeAssembler) {
        this.submissionJudgeService = submissionJudgeService;
        this.judgeAssembler = judgeAssembler;
    }

    @PostMapping("/api/teacher/submissions/{submissionId}/judge")
    public ApiResponse<JudgeResponse> judgeSubmission(@PathVariable Long submissionId) {
        return ApiResponse.success(judgeAssembler.toResponse(
                submissionJudgeService.judgeSubmission(submissionId)
        ));
    }
}
