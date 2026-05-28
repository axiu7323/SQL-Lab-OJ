package com.sqloj.infrastructure.persistence.converter;

import com.sqloj.domain.judge.JudgeError;
import com.sqloj.domain.judge.JudgeResult;
import com.sqloj.domain.judge.JudgeScore;
import com.sqloj.domain.judge.JudgeStatus;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.infrastructure.persistence.entity.JudgeResultDO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JudgeResultConverter {

    public JudgeResult toDomain(JudgeResultDO judgeResultDO) {
        return new JudgeResult(
                SubmissionId.of(judgeResultDO.getSubmissionId()),
                JudgeStatus.valueOf(judgeResultDO.getStatus()),
                new JudgeScore(defaultInt(judgeResultDO.getScore(), 0)),
                JudgeError.of(
                        defaultString(judgeResultDO.getErrorCode()),
                        defaultString(judgeResultDO.getErrorMessage()),
                        defaultString(judgeResultDO.getErrorDetail())
                ),
                defaultLong(judgeResultDO.getExecuteTimeMs(), 0),
                defaultTime(judgeResultDO.getJudgedAt())
        );
    }

    public JudgeResultDO toDO(JudgeResult judgeResult) {
        JudgeResultDO judgeResultDO = new JudgeResultDO();
        judgeResultDO.setId(judgeResult.getSubmissionId().value());
        judgeResultDO.setSubmissionId(judgeResult.getSubmissionId().value());
        judgeResultDO.setStatus(judgeResult.getStatus().name());
        judgeResultDO.setScore(judgeResult.getScore().value());
        judgeResultDO.setErrorCode(judgeResult.getError().code());
        judgeResultDO.setErrorMessage(judgeResult.getError().message());
        judgeResultDO.setErrorDetail(judgeResult.getError().detail());
        judgeResultDO.setExecuteTimeMs(judgeResult.getExecuteTimeMs());
        judgeResultDO.setJudgedAt(judgeResult.getJudgedAt());
        judgeResultDO.setDeleted(0);
        return judgeResultDO;
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
