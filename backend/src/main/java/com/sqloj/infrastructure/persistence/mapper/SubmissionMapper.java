package com.sqloj.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqloj.infrastructure.persistence.entity.SubmissionDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubmissionMapper extends BaseMapper<SubmissionDO> {
}

