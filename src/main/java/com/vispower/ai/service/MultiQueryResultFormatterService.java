package com.vispower.ai.service;

import com.vispower.ai.domain.MultiQueryResult;
import com.vispower.ai.domain.QueryAnalysisResult;
import com.vispower.ai.domain.QueryResult;
import com.vispower.ai.domain.SqlQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MultiQueryResultFormatterService {

    private final QueryResultFormatterService singleFormatterService;

    public MultiQueryResultFormatterService(QueryResultFormatterService singleFormatterService) {
        this.singleFormatterService = singleFormatterService;
    }

    /**
     * 格式化多查询结果
     */
    public String formatMultiQueryResult(MultiQueryResult multiResult, QueryAnalysisResult analysisResult) {
        if (!multiResult.isSuccess()) {
            return "❌ 查询执行失败：" + multiResult.getErrorMessage();
        }

        StringBuilder sb = new StringBuilder();

        if (analysisResult.isMultiQuery()) {
            sb.append("🔍 复合查询结果：\n\n");
        }

        List<QueryResult> queryResults = multiResult.getQueryResults();
        List<SqlQueryResult> sqlResults = multiResult.getSqlResults();
        List<String> subQueries = analysisResult.getSubQueries();

        for (int i = 0; i < queryResults.size(); i++) {
            QueryResult queryResult = queryResults.get(i);

            if (analysisResult.isMultiQuery()) {
                sb.append(String.format("📋 问题 %d：%s\n", i + 1,
                        i < subQueries.size() ? subQueries.get(i) : "查询" + (i + 1)));

                if (i < sqlResults.size()) {
                    sb.append(String.format("🔧 SQL：%s\n", sqlResults.get(i).getSqlInfo().getSql()));
                }
            }

            String formattedResult = singleFormatterService.formatQueryResult(queryResult);
            sb.append(formattedResult);

            if (i < queryResults.size() - 1) {
                sb.append("\n").append("─".repeat(50)).append("\n\n");
            }
        }
        // 添加错误信息（如果有的话）
        if (!multiResult.getErrors().isEmpty()) {
            sb.append("\n\n⚠️ 部分查询出现问题：\n");
            for (String error : multiResult.getErrors()) {
                sb.append("• ").append(error).append("\n");
            }
        }
        return sb.toString();
    }
}