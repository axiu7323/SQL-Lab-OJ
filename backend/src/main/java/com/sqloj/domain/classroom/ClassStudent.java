package com.sqloj.domain.classroom;

import com.sqloj.domain.user.UserId;

import java.time.LocalDateTime;
import java.util.Objects;

public record ClassStudent(ClassId classId, UserId studentId, LocalDateTime joinedAt) {

    public ClassStudent {
        Objects.requireNonNull(classId, "classId must not be null");
        Objects.requireNonNull(studentId, "studentId must not be null");
        joinedAt = joinedAt == null ? LocalDateTime.now() : joinedAt;
    }
}

