package com.sqloj.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("oj_judge_result")
public class JudgeResultDO {

    @TableId(type = IdType.INPUT)
    private Long id;

    private Long submissionId;

    private String status;

    private Integer score;

    private String errorCode;

    private String errorMessage;

    private String errorDetail;

    private Long executeTimeMs;

    private LocalDateTime judgedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}
