package com.vispower.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SqlQueryResult {
    private boolean success;
    private SqlInfo sqlInfo;
    private String errorMessage;

    public static SqlQueryResult success(SqlInfo sqlInfo) {
        return SqlQueryResult.builder()
                .success(true)
                .sqlInfo(sqlInfo)
                .build();
    }

    public static SqlQueryResult error(String errorMessage) {
        return SqlQueryResult.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}