package com.sqloj.domain.judge;

public class ScoreCalculator {

    public JudgeScore calculate(ResultSetDiff diff) {
        return diff.matched() ? JudgeScore.full() : JudgeScore.zero();
    }
}

