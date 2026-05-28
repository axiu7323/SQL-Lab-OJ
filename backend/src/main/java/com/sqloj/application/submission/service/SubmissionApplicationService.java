package com.sqloj.application.submission.service;

import com.sqloj.application.submission.command.SubmitSqlCommand;
import com.sqloj.application.submission.dto.SubmissionDTO;
import com.sqloj.application.submission.dto.SubmissionDetailDTO;
import com.sqloj.application.submission.query.SubmissionQuery;
import com.sqloj.common.enums.ErrorCode;
import com.sqloj.common.exception.BusinessException;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemRepository;
import com.sqloj.domain.shared.PageResult;
import com.sqloj.domain.submission.Submission;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmissionRepository;
import com.sqloj.domain.submission.SubmitSql;
import com.sqloj.domain.user.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmissionApplicationService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;

    public SubmissionApplicationService(SubmissionRepository submissionRepository, ProblemRepository problemRepository) {
        this.submissionRepository = submissionRepository;
        this.problemRepository = problemRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public SubmissionDetailDTO submitSql(SubmitSqlCommand command) {
        ProblemId problemId = ProblemId.of(command.problemId());
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROBLEM_NOT_FOUND));
        if (!problem.canSubmit()) {
            throw new BusinessException(ErrorCode.PROBLEM_NOT_ENABLED);
        }

        Submission submission = Submission.create(
                submissionRepository.nextId(),
                UserId.of(command.userId()),
                problemId,
                SubmitSql.of(command.submitSql())
        );
        // 判题引擎会在 007 任务接入；本轮只创建待判题提交记录。
        submissionRepository.save(submission);
        return toDetailDTO(submission);
    }

    @Transactional(readOnly = true)
    public PageResult<SubmissionDTO> pageMySubmissions(SubmissionQuery query) {
        if (query.studentId() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "userId must not be null");
        }
        PageResult<Submission> page = submissionRepository.page(query.toCriteria());
        return PageResult.of(page.records().stream().map(this::toDTO).toList(), page.total(), page.pageNo(), page.pageSize());
    }

    @Transactional(readOnly = true)
    public SubmissionDetailDTO getMySubmissionDetail(Long userId, Long submissionId) {
        Submission submission = findRequired(submissionId);
        if (!submission.getUserId().equals(UserId.of(userId))) {
            throw new BusinessException(ErrorCode.PERMISSION_DENIED);
        }
        return toDetailDTO(submission);
    }

    @Transactional(readOnly = true)
    public PageResult<SubmissionDTO> pageTeacherSubmissions(SubmissionQuery query) {
        PageResult<Submission> page = submissionRepository.page(query.toCriteria());
        return PageResult.of(page.records().stream().map(this::toDTO).toList(), page.total(), page.pageNo(), page.pageSize());
    }

    @Transactional(readOnly = true)
    public SubmissionDetailDTO getTeacherSubmissionDetail(Long submissionId) {
        return toDetailDTO(findRequired(submissionId));
    }

    private Submission findRequired(Long submissionId) {
        return submissionRepository.findById(SubmissionId.of(submissionId))
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBMISSION_NOT_FOUND));
    }

    private SubmissionDTO toDTO(Submission submission) {
        return new SubmissionDTO(
                submission.getId().value(),
                submission.getUserId().value(),
                submission.getProblemId().value(),
                submission.getStatus(),
                submission.getScore().value(),
                submission.getError().message(),
                submission.getExecuteTimeMs(),
                submission.getSubmittedAt(),
                submission.getJudgedAt()
        );
    }

    private SubmissionDetailDTO toDetailDTO(Submission submission) {
        return new SubmissionDetailDTO(
                submission.getId().value(),
                submission.getUserId().value(),
                submission.getProblemId().value(),
                submission.getSubmitSql().value(),
                submission.getStatus(),
                submission.getScore().value(),
                submission.getError().message(),
                submission.getExecuteTimeMs(),
                submission.getSubmittedAt(),
                submission.getJudgedAt()
        );
    }
}
