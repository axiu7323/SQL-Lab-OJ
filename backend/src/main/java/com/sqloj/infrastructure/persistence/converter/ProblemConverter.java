package com.sqloj.infrastructure.persistence.converter;

import com.sqloj.domain.problem.JudgeConfig;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemCase;
import com.sqloj.domain.problem.ProblemCategory;
import com.sqloj.domain.problem.ProblemDifficulty;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemStatus;
import com.sqloj.domain.problem.SqlScript;
import com.sqloj.infrastructure.persistence.entity.ProblemDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProblemConverter {

    private static final String DEFAULT_CASE_NAME = "default";

    public Problem toDomain(ProblemDO problemDO) {
        Problem problem = new Problem(
                ProblemId.of(problemDO.getId()),
                problemDO.getTitle(),
                problemDO.getDescription(),
                new ProblemCategory(problemDO.getCategoryId(), defaultCategoryName(problemDO)),
                ProblemDifficulty.valueOf(problemDO.getDifficulty()),
                ProblemStatus.valueOf(problemDO.getStatus()),
                new JudgeConfig(
                        defaultInt(problemDO.getTimeLimitMs(), JudgeConfig.DEFAULT_TIME_LIMIT_MS),
                        defaultInt(problemDO.getMaxRows(), JudgeConfig.DEFAULT_MAX_ROWS),
                        Boolean.TRUE.equals(problemDO.getOrderSensitive()),
                        !Boolean.FALSE.equals(problemDO.getCheckColumnName())
                ),
                List.of(new ProblemCase(
                        problemDO.getId(),
                        DEFAULT_CASE_NAME,
                        SqlScript.of(problemDO.getInitSchemaSql()),
                        SqlScript.of(problemDO.getInitDataSql()),
                        SqlScript.of(problemDO.getStandardSql()),
                        false
                ))
        );
        problem.updateScore(defaultInt(problemDO.getScore(), 100));
        return problem;
    }

    public ProblemDO toDO(Problem problem) {
        ProblemCase problemCase = problem.getCases().get(0);
        JudgeConfig judgeConfig = problem.getJudgeConfig();
        ProblemDO problemDO = new ProblemDO();
        problemDO.setId(problem.getId().value());
        problemDO.setTitle(problem.getTitle());
        problemDO.setDescription(problem.getDescription());
        problemDO.setCategoryId(problem.getCategory().id());
        problemDO.setCategoryName(problem.getCategory().name());
        problemDO.setDifficulty(problem.getDifficulty().name());
        problemDO.setScore(problem.getScore());
        problemDO.setInitSchemaSql(problemCase.schemaSql().content());
        problemDO.setInitDataSql(problemCase.initDataSql().content());
        problemDO.setStandardSql(problemCase.expectedSql().content());
        problemDO.setOrderSensitive(judgeConfig.orderSensitive());
        problemDO.setCheckColumnName(judgeConfig.checkColumnName());
        problemDO.setTimeLimitMs(judgeConfig.timeLimitMs());
        problemDO.setMaxRows(judgeConfig.maxRows());
        problemDO.setStatus(problem.getStatus().name());
        problemDO.setDeleted(problem.isDeleted() ? 1 : 0);
        return problemDO;
    }

    private int defaultInt(Integer value, int defaultValue) {
        return value == null ? defaultValue : value;
    }

    private String defaultCategoryName(ProblemDO problemDO) {
        String categoryName = problemDO.getCategoryName();
        return categoryName == null || categoryName.isBlank() ? "category-" + problemDO.getCategoryId() : categoryName;
    }
}

