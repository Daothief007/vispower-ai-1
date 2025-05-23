package com.vispower.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryResult {
    private boolean success;
    private List<Map<String, Object>> data;
    private QueryType queryType;
    private String errorMessage;

    public static QueryResult success(List<Map<String, Object>> data, QueryType queryType) {
        return QueryResult.builder()
                .success(true)
                .data(data)
                .queryType(queryType)
                .build();
    }

    public static QueryResult error(String errorMessage) {
        return QueryResult.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}