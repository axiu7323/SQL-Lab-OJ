package com.sqloj.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqloj.domain.shared.PageResult;
import com.sqloj.domain.submission.Submission;
import com.sqloj.domain.submission.SubmissionId;
import com.sqloj.domain.submission.SubmissionPageCriteria;
import com.sqloj.domain.submission.SubmissionRepository;
import com.sqloj.infrastructure.persistence.converter.SubmissionConverter;
import com.sqloj.infrastructure.persistence.entity.SubmissionDO;
import com.sqloj.infrastructure.persistence.mapper.SubmissionMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private final SubmissionMapper submissionMapper;
    private final SubmissionConverter submissionConverter;
    private final AtomicLong idGenerator = new AtomicLong(System.currentTimeMillis());

    public SubmissionRepositoryImpl(SubmissionMapper submissionMapper, SubmissionConverter submissionConverter) {
        this.submissionMapper = submissionMapper;
        this.submissionConverter = submissionConverter;
    }

    @Override
    public SubmissionId nextId() {
        return SubmissionId.of(idGenerator.incrementAndGet());
    }

    @Override
    public Optional<Submission> findById(SubmissionId id) {
        return Optional.ofNullable(submissionMapper.selectById(id.value()))
                .map(submissionConverter::toDomain);
    }

    @Override
    public PageResult<Submission> page(SubmissionPageCriteria criteria) {
        Page<SubmissionDO> page = new Page<>(criteria.pageNo(), criteria.pageSize());
        LambdaQueryWrapper<SubmissionDO> wrapper = Wrappers.lambdaQuery();
        if (criteria.studentId() != null) {
            wrapper.eq(SubmissionDO::getUserId, criteria.studentId().value());
        }
        if (criteria.problemId() != null) {
            wrapper.eq(SubmissionDO::getProblemId, criteria.problemId().value());
        }
        if (criteria.status() != null) {
            wrapper.eq(SubmissionDO::getStatus, criteria.status().name());
        }
        if (criteria.startTime() != null) {
            wrapper.ge(SubmissionDO::getSubmittedAt, criteria.startTime());
        }
        if (criteria.endTime() != null) {
            wrapper.le(SubmissionDO::getSubmittedAt, criteria.endTime());
        }
        wrapper.orderByDesc(SubmissionDO::getSubmittedAt).orderByDesc(SubmissionDO::getId);
        Page<SubmissionDO> resultPage = submissionMapper.selectPage(page, wrapper);
        return PageResult.of(
                resultPage.getRecords().stream().map(submissionConverter::toDomain).toList(),
                resultPage.getTotal(),
                resultPage.getCurrent(),
                resultPage.getSize()
        );
    }

    @Override
    public void save(Submission submission) {
        SubmissionDO submissionDO = submissionConverter.toDO(submission);
        if (submissionMapper.selectById(submission.getId().value()) == null) {
            submissionMapper.insert(submissionDO);
        } else {
            submissionMapper.updateById(submissionDO);
        }
    }
}

