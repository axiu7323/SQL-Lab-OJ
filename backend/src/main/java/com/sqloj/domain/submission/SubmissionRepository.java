package com.sqloj.domain.submission;

import java.util.Optional;

public interface SubmissionRepository {

    Optional<Submission> findById(SubmissionId id);

    void save(Submission submission);
}

