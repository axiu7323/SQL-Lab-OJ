package com.sqloj.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sqloj.domain.judge.JudgeResult;
import com.sqloj.domain.judge.JudgeResultRepository;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.infrastructure.persistence.converter.JudgeResultConverter;
import com.sqloj.infrastructure.persistence.entity.JudgeResultDO;
import com.sqloj.infrastructure.persistence.mapper.JudgeResultMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JudgeResultRepositoryImpl implements JudgeResultRepository {

    private final JudgeResultMapper judgeResultMapper;
    private final JudgeResultConverter judgeResultConverter;

    public JudgeResultRepositoryImpl(JudgeResultMapper judgeResultMapper, JudgeResultConverter judgeResultConverter) {
        this.judgeResultMapper = judgeResultMapper;
        this.judgeResultConverter = judgeResultConverter;
    }

    @Override
    public Optional<JudgeResult> findBySubmissionId(SubmissionId submissionId) {
        JudgeResultDO judgeResultDO = judgeResultMapper.selectOne(Wrappers.<JudgeResultDO>lambdaQuery()
                .eq(JudgeResultDO::getSubmissionId, submissionId.value())
                .last("LIMIT 1"));
        return Optional.ofNullable(judgeResultDO).map(judgeResultConverter::toDomain);
    }

    @Override
    public void save(JudgeResult result) {
        JudgeResultDO judgeResultDO = judgeResultConverter.toDO(result);
        if (judgeResultMapper.selectById(judgeResultDO.getId()) == null) {
            judgeResultMapper.insert(judgeResultDO);
        } else {
            judgeResultMapper.updateById(judgeResultDO);
        }
    }
}
