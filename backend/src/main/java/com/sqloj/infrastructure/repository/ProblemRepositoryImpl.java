package com.sqloj.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqloj.domain.problem.Problem;
import com.sqloj.domain.problem.ProblemId;
import com.sqloj.domain.problem.ProblemPageCriteria;
import com.sqloj.domain.problem.ProblemRepository;
import com.sqloj.domain.problem.ProblemStatus;
import com.sqloj.domain.shared.PageResult;
import com.sqloj.infrastructure.persistence.converter.ProblemConverter;
import com.sqloj.infrastructure.persistence.entity.ProblemDO;
import com.sqloj.infrastructure.persistence.mapper.ProblemMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProblemRepositoryImpl implements ProblemRepository {

    private final ProblemMapper problemMapper;
    private final ProblemConverter problemConverter;
    private final AtomicLong idGenerator = new AtomicLong(System.currentTimeMillis());

    public ProblemRepositoryImpl(ProblemMapper problemMapper, ProblemConverter problemConverter) {
        this.problemMapper = problemMapper;
        this.problemConverter = problemConverter;
    }

    @Override
    public ProblemId nextId() {
        return ProblemId.of(idGenerator.incrementAndGet());
    }

    @Override
    public Optional<Problem> findById(ProblemId id) {
        return Optional.ofNullable(problemMapper.selectById(id.value()))
                .map(problemConverter::toDomain);
    }

    @Override
    public PageResult<Problem> page(ProblemPageCriteria criteria) {
        Page<ProblemDO> page = new Page<>(criteria.pageNo(), criteria.pageSize());
        LambdaQueryWrapper<ProblemDO> wrapper = Wrappers.lambdaQuery();
        if (!criteria.keyword().isBlank()) {
            wrapper.like(ProblemDO::getTitle, criteria.keyword());
        }
        if (criteria.categoryId() != null) {
            wrapper.eq(ProblemDO::getCategoryId, criteria.categoryId());
        }
        if (criteria.difficulty() != null) {
            wrapper.eq(ProblemDO::getDifficulty, criteria.difficulty().name());
        }
        if (criteria.enabledOnly()) {
            wrapper.eq(ProblemDO::getStatus, ProblemStatus.ENABLED.name());
        } else if (criteria.status() != null) {
            wrapper.eq(ProblemDO::getStatus, criteria.status().name());
        }
        wrapper.orderByDesc(ProblemDO::getUpdatedAt).orderByDesc(ProblemDO::getId);
        Page<ProblemDO> resultPage = problemMapper.selectPage(page, wrapper);
        return PageResult.of(
                resultPage.getRecords().stream().map(problemConverter::toDomain).toList(),
                resultPage.getTotal(),
                resultPage.getCurrent(),
                resultPage.getSize()
        );
    }

    @Override
    public void save(Problem problem) {
        ProblemDO problemDO = problemConverter.toDO(problem);
        if (problemMapper.selectById(problem.getId().value()) == null) {
            problemMapper.insert(problemDO);
        } else {
            problemMapper.updateById(problemDO);
        }
    }

    @Override
    public void deleteById(ProblemId id) {
        problemMapper.deleteById(id.value());
    }
}

