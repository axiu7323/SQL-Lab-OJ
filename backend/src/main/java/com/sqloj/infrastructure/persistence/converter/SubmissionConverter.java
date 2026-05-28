package com.sqloj.infrastructure.persistence.converter;

import com.sqloj.domain.judge.JudgeError;
import com.sqloj.domain.judge.JudgeScore;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.submission.Submission;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmissionStatus;
import com.sqloj.domain.submission.SubmitSql;
import com.sqloj.domain.user.UserId;
import com.sqloj.infrastructure.persistence.entity.SubmissionDO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubmissionConverter {

    public Submission toDomain(SubmissionDO submissionDO) {
        return new Submission(
                SubmissionId.of(submissionDO.getId()),
                UserId.of(submissionDO.getUserId()),
                ProblemId.of(submissionDO.getProblemId()),
                SubmitSql.of(submissionDO.getSubmitSql()),
                SubmissionStatus.valueOf(submissionDO.getStatus()),
                new JudgeScore(defaultInt(submissionDO.getScore(), 0)),
                JudgeError.of("", defaultString(submissionDO.getErrorMessage())),
                defaultLong(submissionDO.getExecuteTimeMs(), 0),
                defaultTime(submissionDO.getSubmittedAt()),
                submissionDO.getJudgedAt()
        );
    }

    public SubmissionDO toDO(Submission submission) {
        SubmissionDO submissionDO = new SubmissionDO();
        submissionDO.setId(submission.getId().value());
        submissionDO.setUserId(submission.getUserId().value());
        submissionDO.setProblemId(submission.getProblemId().value());
        submissionDO.setSubmitSql(submission.getSubmitSql().value());
        submissionDO.setStatus(submission.getStatus().name());
        submissionDO.setScore(submission.getScore().value());
        submissionDO.setErrorMessage(submission.getError().message());
        submissionDO.setExecuteTimeMs(submission.getExecuteTimeMs());
        submissionDO.setSubmittedAt(submission.getSubmittedAt());
        submissionDO.setJudgedAt(submission.getJudgedAt());
        submissionDO.setDeleted(0);
        return submissionDO;
    }

    private int defaultInt(Integer value, int defaultValue) {
        return value == null ? defaultValue : value;
    }

    private long defaultLong(Long value, long defaultValue) {
        return value == null ? defaultValue : value;
    }

    private String defaultString(String value) {
        return value == null ? "" : value;
    }

    private LocalDateTime defaultTime(LocalDateTime value) {
        return value == null ? LocalDateTime.now() : value;
    }
}

