package com.vispower.ai.service;

import com.vispower.ai.domain.MultiSqlQueryResult;
import com.vispower.ai.domain.SqlQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MultiQuerySqlGeneratorService {

    private final SmartSqlGeneratorService sqlGeneratorService;

    public MultiQuerySqlGeneratorService(SmartSqlGeneratorService sqlGeneratorService) {
        this.sqlGeneratorService = sqlGeneratorService;
    }

    /**
     * 为多个子查询生成SQL
     */
    public MultiSqlQueryResult generateMultiSql(List<String> subQueries) {
        List<SqlQueryResult> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < subQueries.size(); i++) {
            String subQuery = subQueries.get(i);
            log.info("处理子查询 {}: {}", i + 1, subQuery);

            try {
                SqlQueryResult result = sqlGeneratorService.generateSql(subQuery);
                if (result.isSuccess()) {
                    results.add(result);
                    log.info("子查询 {} SQL生成成功: {}", i + 1, result.getSqlInfo().getSql());
                } else {
                    errors.add(String.format("子查询 %d 失败: %s", i + 1, result.getErrorMessage()));
                }
            } catch (Exception e) {
                log.error("子查询 {} 处理异常: {}", i + 1, subQuery, e);
                errors.add(String.format("子查询 %d 异常: %s", i + 1, e.getMessage()));
            }
        }

        if (results.isEmpty()) {
            return MultiSqlQueryResult.error("所有子查询都失败了：" + String.join("; ", errors));
        }

        return MultiSqlQueryResult.success(results, errors);
    }
}
