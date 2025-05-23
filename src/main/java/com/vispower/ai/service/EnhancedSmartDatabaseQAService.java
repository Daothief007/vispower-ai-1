package com.vispower.ai.service;

import com.vispower.ai.domain.DatabaseQAResponse;
import com.vispower.ai.domain.MultiQueryResult;
import com.vispower.ai.domain.MultiSqlQueryResult;
import com.vispower.ai.domain.QueryAnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnhancedSmartDatabaseQAService {

    private final QueryDecompositionService decompositionService;
    private final MultiQuerySqlGeneratorService multiSqlGeneratorService;
    private final MultiQueryExecutorService multiQueryExecutorService;
    private final MultiQueryResultFormatterService multiFormatterService;
    private final SmartDatabaseQAService singleQueryService; // 原有的单查询服务

    public EnhancedSmartDatabaseQAService(QueryDecompositionService decompositionService,
                                          MultiQuerySqlGeneratorService multiSqlGeneratorService,
                                          MultiQueryExecutorService multiQueryExecutorService,
                                          MultiQueryResultFormatterService multiFormatterService,
                                          SmartDatabaseQAService singleQueryService) {
        this.decompositionService = decompositionService;
        this.multiSqlGeneratorService = multiSqlGeneratorService;
        this.multiQueryExecutorService = multiQueryExecutorService;
        this.multiFormatterService = multiFormatterService;
        this.singleQueryService = singleQueryService;
    }

    public DatabaseQAResponse query(String naturalQuery) {
        log.info("收到查询请求: {}", naturalQuery);

        try {
            // 1. 分析查询是否为复合查询
            QueryAnalysisResult analysisResult = decompositionService.analyzeQuery(naturalQuery);

            if (!analysisResult.isMultiQuery()) {
                // 单查询，使用原有服务
                log.info("识别为单查询，使用单查询处理流程");
                return singleQueryService.query(naturalQuery);
            }

            // 2. 复合查询处理
            log.info("识别为复合查询，包含{}个子查询", analysisResult.getSubQueries().size());

            // 3. 生成多个SQL
            MultiSqlQueryResult multiSqlResult = multiSqlGeneratorService.generateMultiSql(
                    analysisResult.getSubQueries());

            if (!multiSqlResult.isSuccess()) {
                return DatabaseQAResponse.error(multiSqlResult.getErrorMessage());
            }

            // 4. 执行多个查询
            MultiQueryResult multiQueryResult = multiQueryExecutorService.executeMultiQuery(
                    multiSqlResult.getSqlResults());

            if (!multiQueryResult.isSuccess()) {
                return DatabaseQAResponse.error(multiQueryResult.getErrorMessage());
            }

            // 5. 格式化结果
            String formattedResult = multiFormatterService.formatMultiQueryResult(
                    multiQueryResult, analysisResult);

            // 6. 构建响应
            return buildMultiQueryResponse(multiQueryResult, formattedResult);

        } catch (Exception e) {
            log.error("处理查询请求失败", e);
            return DatabaseQAResponse.error("系统处理失败: " + e.getMessage());
        }
    }

    private DatabaseQAResponse buildMultiQueryResponse(MultiQueryResult multiResult, String formattedResult) {
        // 合并所有SQL语句
        List<String> allSqls = multiResult.getSqlResults().stream()
                .map(result -> result.getSqlInfo().getSql())
                .collect(Collectors.toList());

        String combinedSql = String.join(";\n\n", allSqls);

        // 合并所有数据
        List<Map<String, Object>> allData = multiResult.getQueryResults().stream()
                .flatMap(result -> result.getData().stream())
                .collect(Collectors.toList());

        return DatabaseQAResponse.success(combinedSql, allData, formattedResult);
    }
}