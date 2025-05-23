package com.vispower.ai.service;

import com.vispower.ai.domain.DatabaseQAResponse;
import com.vispower.ai.domain.QueryResult;
import com.vispower.ai.domain.QueryType;
import com.vispower.ai.domain.SqlQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SmartDatabaseQAService {

    private final SmartSqlGeneratorService sqlGeneratorService;
    private final SmartQueryExecutorService queryExecutorService;
    private final QueryResultFormatterService formatterService;

    public SmartDatabaseQAService(SmartSqlGeneratorService sqlGeneratorService,
                                  SmartQueryExecutorService queryExecutorService,
                                  QueryResultFormatterService formatterService) {
        this.sqlGeneratorService = sqlGeneratorService;
        this.queryExecutorService = queryExecutorService;
        this.formatterService = formatterService;
    }

    public DatabaseQAResponse query(String naturalQuery) {
        log.info("收到智能查询请求: {}", naturalQuery);

        // 1. 生成SQL
        SqlQueryResult sqlResult = sqlGeneratorService.generateSql(naturalQuery);
        if (!sqlResult.isSuccess()) {
            return DatabaseQAResponse.error(sqlResult.getErrorMessage());
        }

        // 2. 执行查询
        QueryResult queryResult = queryExecutorService.executeQuery(sqlResult.getSqlInfo());
        if (!queryResult.isSuccess()) {
            return DatabaseQAResponse.error(queryResult.getErrorMessage());
        }

        // 3. 格式化结果
        String formattedResult = formatterService.formatQueryResult(queryResult);

        return DatabaseQAResponse.success(
                sqlResult.getSqlInfo().getSql(),
                queryResult.getData(),
                formattedResult
        );
    }
}
