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
@TableName("oj_submission")
public class SubmissionDO {

    @TableId(type = IdType.INPUT)
    private Long id;

    private Long userId;

    private Long problemId;

    private String submitSql;

    private String status;

    private Integer score;

    private String errorMessage;

    private Long executeTimeMs;

    private LocalDateTime submittedAt;

    private LocalDateTime judgedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}

