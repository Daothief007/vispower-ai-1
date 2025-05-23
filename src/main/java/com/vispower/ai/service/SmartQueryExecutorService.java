package com.vispower.ai.service;

import com.vispower.ai.domain.QueryResult;
import com.vispower.ai.domain.QueryType;
import com.vispower.ai.domain.SqlInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SmartQueryExecutorService {

    private final JdbcTemplate jdbcTemplate;

    public SmartQueryExecutorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public QueryResult executeQuery(SqlInfo sqlInfo) {
        try {
            if (sqlInfo.getQueryType() == QueryType.STATISTICS) {
                return executeStatisticsQuery(sqlInfo.getSql());
            } else {
                return executeDetailQuery(sqlInfo.getSql());
            }
        } catch (Exception e) {
            log.error("执行SQL查询失败: {}", sqlInfo.getSql(), e);
            return QueryResult.error("查询执行失败: " + e.getMessage());
        }
    }

    private QueryResult executeStatisticsQuery(String sql) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return QueryResult.success(results, QueryType.STATISTICS);
    }

    private QueryResult executeDetailQuery(String sql) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return QueryResult.success(results, QueryType.DETAIL);
    }
}
