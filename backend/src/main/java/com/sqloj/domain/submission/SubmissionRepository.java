package com.sqloj.domain.submission;

import com.sqloj.domain.shared.PageResult;

import java.util.Optional;

public interface SubmissionRepository {

    SubmissionId nextId();

    Optional<Submission> findById(SubmissionId id);

    PageResult<Submission> page(SubmissionPageCriteria criteria);

    void save(Submission submission);
}

