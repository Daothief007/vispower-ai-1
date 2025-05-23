package com.vispower.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiSqlQueryResult {
    private boolean success;
    private List<SqlQueryResult> sqlResults;
    private List<String> errors;
    private String errorMessage;

    public static MultiSqlQueryResult success(List<SqlQueryResult> results, List<String> errors) {
        return MultiSqlQueryResult.builder()
                .success(true)
                .sqlResults(results)
                .errors(errors)
                .build();
    }

    public static MultiSqlQueryResult error(String errorMessage) {
        return MultiSqlQueryResult.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}