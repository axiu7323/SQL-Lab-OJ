package com.sqloj.application.judge.service;

import com.sqloj.application.judge.command.JudgeTaskCommand;
import com.sqloj.application.judge.dto.JudgeResultDTO;
import org.springframework.stereotype.Service;

@Service
public class SubmissionJudgeService {

    private final JudgeApplicationService judgeApplicationService;

    public SubmissionJudgeService(JudgeApplicationService judgeApplicationService) {
        this.judgeApplicationService = judgeApplicationService;
    }

    public JudgeResultDTO judgeSubmission(Long submissionId) {
        return judgeApplicationService.judge(new JudgeTaskCommand(submissionId));
    }
}
