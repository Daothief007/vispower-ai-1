package com.vispower.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryAnalysisResult {
    private boolean isMultiQuery;
    private List<String> subQueries;
    private String originalQuery;

    public static QueryAnalysisResult singleQuery(String query) {
        return QueryAnalysisResult.builder()
                .isMultiQuery(false)
                .subQueries(List.of(query))
                .originalQuery(query)
                .build();
    }

    public static QueryAnalysisResult multiQuery(List<String> subQueries) {
        return QueryAnalysisResult.builder()
                .isMultiQuery(true)
                .subQueries(new ArrayList<>(subQueries))
                .originalQuery(String.join("，", subQueries))
                .build();
    }
}