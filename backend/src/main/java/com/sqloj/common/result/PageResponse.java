package com.sqloj.common.result;

import java.util.List;

public record PageResponse<T>(List<T> records, long total, long pageNo, long pageSize) {

    public PageResponse {
        records = records == null ? List.of() : List.copyOf(records);
    }

    public static <T> PageResponse<T> of(List<T> records, long total, long pageNo, long pageSize) {
        return new PageResponse<>(records, total, pageNo, pageSize);
    }
}

