package com.sqloj.domain.problem;

import com.sqloj.domain.shared.exception.DomainException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Problem {

    private final ProblemId id;
    private String title;
    private String description;
    private ProblemCategory category;
    private ProblemDifficulty difficulty;
    private ProblemStatus status;
    private JudgeConfig judgeConfig;
    private List<ProblemCase> cases;

    public Problem(
            ProblemId id,
            String title,
            String description,
            ProblemCategory category,
            ProblemDifficulty difficulty,
            ProblemStatus status,
            JudgeConfig judgeConfig,
            List<ProblemCase> cases
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.title = normalizeRequiredText(title, "problem title must not be blank");
        this.description = normalizeRequiredText(description, "problem description must not be blank");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.difficulty = Objects.requireNonNull(difficulty, "difficulty must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.judgeConfig = Objects.requireNonNull(judgeConfig, "judgeConfig must not be null");
        replaceCases(cases);
    }

    public static Problem draft(
            ProblemId id,
            String title,
            String description,
            ProblemCategory category,
            ProblemDifficulty difficulty,
            JudgeConfig judgeConfig,
            List<ProblemCase> cases
    ) {
        return new Problem(id, title, description, category, difficulty, ProblemStatus.DRAFT, judgeConfig, cases);
    }

    public boolean canSubmit() {
        return status == ProblemStatus.ENABLED;
    }

    public void validateForPublish() {
        if (title.isBlank()) {
            throw new DomainException("problem title must not be blank");
        }
        if (description.isBlank()) {
            throw new DomainException("problem description must not be blank");
        }
        if (cases.isEmpty()) {
            throw new DomainException("problem must contain at least one case");
        }
    }

    public void enable() {
        validateForPublish();
        this.status = ProblemStatus.ENABLED;
    }

    public void disable() {
        this.status = ProblemStatus.DISABLED;
    }

    public void updateBasicInfo(String title, String description, ProblemCategory category, ProblemDifficulty difficulty) {
        this.title = normalizeRequiredText(title, "problem title must not be blank");
        this.description = normalizeRequiredText(description, "problem description must not be blank");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.difficulty = Objects.requireNonNull(difficulty, "difficulty must not be null");
    }

    public void updateJudgeConfig(JudgeConfig judgeConfig) {
        this.judgeConfig = Objects.requireNonNull(judgeConfig, "judgeConfig must not be null");
    }

    public void replaceCases(List<ProblemCase> cases) {
        if (cases == null || cases.isEmpty()) {
            throw new DomainException("problem must contain at least one case");
        }
        this.cases = new ArrayList<>(cases);
    }

    public void addCase(ProblemCase problemCase) {
        this.cases.add(Objects.requireNonNull(problemCase, "problemCase must not be null"));
    }

    public ProblemId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProblemCategory getCategory() {
        return category;
    }

    public ProblemDifficulty getDifficulty() {
        return difficulty;
    }

    public ProblemStatus getStatus() {
        return status;
    }

    public JudgeConfig getJudgeConfig() {
        return judgeConfig;
    }

    public List<ProblemCase> getCases() {
        return List.copyOf(cases);
    }

    private static String normalizeRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new DomainException(message);
        }
        return value.trim();
    }
}

