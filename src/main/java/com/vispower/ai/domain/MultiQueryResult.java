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
public class MultiQueryResult {
    private boolean success;
    private List<QueryResult> queryResults;
    private List<SqlQueryResult> sqlResults;
    private List<String> errors;
    private String errorMessage;

    public static MultiQueryResult success(List<QueryResult> queryResults,
                                           List<SqlQueryResult> sqlResults,
                                           List<String> errors) {
        return MultiQueryResult.builder()
                .success(true)
                .queryResults(queryResults)
                .sqlResults(sqlResults)
                .errors(errors)
                .build();
    }

    public static MultiQueryResult error(String errorMessage) {
        return MultiQueryResult.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}