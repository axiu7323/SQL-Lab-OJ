package com.sqloj.interfaces.request;

import com.sqloj.domain.submission.SubmissionStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubmissionPageRequest {

    private Long pageNo = 1L;
    private Long pageSize = 10L;
    private Long userId;
    private Long studentId;
    private Long problemId;
    private SubmissionStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;
}

