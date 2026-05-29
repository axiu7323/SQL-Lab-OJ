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
@TableName("oj_problem")
public class ProblemDO {

    @TableId(type = IdType.INPUT)
    private Long id;

    private String title;

    private String description;

    private Long categoryId;

    private String categoryName;

    private String difficulty;

    private Integer score;

    private String initSchemaSql;

    private String initDataSql;

    private String standardSql;

    private Boolean orderSensitive;

    private Boolean checkColumnName;

    private Integer timeLimitMs;

    private Integer maxRows;

    private String status;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}

