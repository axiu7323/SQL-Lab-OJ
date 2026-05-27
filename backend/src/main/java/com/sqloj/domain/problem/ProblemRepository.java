package com.sqloj.domain.problem;

import java.util.Optional;

public interface ProblemRepository {

    Optional<Problem> findById(ProblemId id);

    void save(Problem problem);
}

