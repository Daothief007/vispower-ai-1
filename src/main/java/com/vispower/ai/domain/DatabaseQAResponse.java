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
public class DatabaseQAResponse {
    private boolean success;
    private String generatedSql;
    private List<Map<String, Object>> data;
    private String formattedResult;
    private String errorMessage;

    public static DatabaseQAResponse success(String sql, List<Map<String, Object>> data, String formattedResult) {
        return DatabaseQAResponse.builder()
                .success(true)
                .generatedSql(sql)
                .data(data)
                .formattedResult(formattedResult)
                .build();
    }

    public static DatabaseQAResponse error(String errorMessage) {
        return DatabaseQAResponse.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}