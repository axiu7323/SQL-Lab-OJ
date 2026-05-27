package com.sqloj.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sqloj.infrastructure.persistence.entity.ProblemDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper extends BaseMapper<ProblemDO> {
}

