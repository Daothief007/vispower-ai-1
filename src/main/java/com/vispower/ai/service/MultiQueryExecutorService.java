package com.vispower.ai.service;

import com.vispower.ai.domain.MultiQueryResult;
import com.vispower.ai.domain.QueryResult;
import com.vispower.ai.domain.SqlQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MultiQueryExecutorService {

    private final SmartQueryExecutorService queryExecutorService;

    public MultiQueryExecutorService(SmartQueryExecutorService queryExecutorService) {
        this.queryExecutorService = queryExecutorService;
    }

    /**
     * 执行多个SQL查询
     */
    public MultiQueryResult executeMultiQuery(List<SqlQueryResult> sqlResults) {
        List<QueryResult> queryResults = new ArrayList<>();
        List<String> executionErrors = new ArrayList<>();

        for (int i = 0; i < sqlResults.size(); i++) {
            SqlQueryResult sqlResult = sqlResults.get(i);

            try {
                log.info("执行第 {} 个查询: {}", i + 1, sqlResult.getSqlInfo().getSql());
                QueryResult result = queryExecutorService.executeQuery(sqlResult.getSqlInfo());

                if (result.isSuccess()) {
                    queryResults.add(result);
                    log.info("第 {} 个查询执行成功，返回 {} 条记录", i + 1, result.getData().size());
                } else {
                    executionErrors.add(String.format("查询 %d 执行失败: %s", i + 1, result.getErrorMessage()));
                }
            } catch (Exception e) {
                log.error("第 {} 个查询执行异常", i + 1, e);
                executionErrors.add(String.format("查询 %d 执行异常: %s", i + 1, e.getMessage()));
            }
        }

        if (queryResults.isEmpty()) {
            return MultiQueryResult.error("所有查询执行都失败了：" + String.join("; ", executionErrors));
        }

        return MultiQueryResult.success(queryResults, sqlResults, executionErrors);
    }
}