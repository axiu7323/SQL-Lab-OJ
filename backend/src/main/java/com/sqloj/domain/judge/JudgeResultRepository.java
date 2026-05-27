package com.sqloj.domain.judge;

import com.sqloj.domain.submission.SubmissionId;

import java.util.Optional;

public interface JudgeResultRepository {

    Optional<JudgeResult> findBySubmissionId(SubmissionId submissionId);

    void save(JudgeResult judgeResult);
}

