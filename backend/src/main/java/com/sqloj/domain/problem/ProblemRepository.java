package com.sqloj.domain.problem;

import com.sqloj.domain.shared.PageResult;

import java.util.Optional;

public interface ProblemRepository {

    ProblemId nextId();

    Optional<Problem> findById(ProblemId id);

    PageResult<Problem> page(ProblemPageCriteria criteria);

    void save(Problem problem);

    void deleteById(ProblemId id);
}

