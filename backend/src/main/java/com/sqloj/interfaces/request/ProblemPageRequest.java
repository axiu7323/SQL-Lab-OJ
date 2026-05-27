package com.sqloj.interfaces.request;

import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemPageRequest {

    private Long pageNo = 1L;
    private Long pageSize = 10L;
    private String keyword;
    private Long categoryId;
    private ProblemDifficulty difficulty;
    private ProblemStatus status;
}

