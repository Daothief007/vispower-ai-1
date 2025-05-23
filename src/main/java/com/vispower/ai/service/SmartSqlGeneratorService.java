package com.vispower.ai.service;

import com.vispower.ai.config.TableMetadataManager;
import com.vispower.ai.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SmartSqlGeneratorService {

    private final ChatClient chatClient;
    private final TableMetadataManager metadataManager;
    private final EnumMappingService enumMappingService;

    public SmartSqlGeneratorService(ChatClient chatClient,
                                    TableMetadataManager metadataManager,
                                    EnumMappingService enumMappingService) {
        this.chatClient = chatClient;
        this.metadataManager = metadataManager;
        this.enumMappingService = enumMappingService;
    }

    public SqlQueryResult generateSql(String naturalQuery) {
        try {
            // 0. 预处理查询，转换枚举值
            String preprocessedQuery = enumMappingService.preprocessQuery(naturalQuery);
            log.debug("预处理后的查询: {}", preprocessedQuery);

            // 1. 确定涉及的表
            List<TableMetadata> relevantTables = identifyRelevantTables(preprocessedQuery);

            if (relevantTables.isEmpty()) {
                return SqlQueryResult.error("无法识别相关的数据表");
            }

            log.info("识别到相关表: {}",
                    relevantTables.stream().map(TableMetadata::getTableName).collect(Collectors.joining(", ")));

            // 2. 生成表结构prompt
            String schemaPrompt = buildSchemaPrompt(relevantTables);

            // 3. 构建完整的prompt
            String fullPrompt = buildFullPrompt(preprocessedQuery, schemaPrompt);

            // 4. 调用AI生成SQL
            String aiResponse = chatClient.prompt()
                    .user(fullPrompt)
                    .call()
                    .content();

            log.debug("AI生成的SQL响应: {}", aiResponse);

            // 5. 解析AI响应
            SqlInfo sqlInfo = parseSqlResponse(aiResponse);

            return SqlQueryResult.success(sqlInfo);

        } catch (Exception e) {
            log.error("生成SQL失败", e);
            return SqlQueryResult.error("生成SQL查询失败: " + e.getMessage());
        }
    }

    private List<TableMetadata> identifyRelevantTables(String query) {
        List<TableMetadata> allTables = metadataManager.getAllTableMetadata();

        if (allTables.size() <= 1) {
            return allTables;
        }

        try {
            return identifyTablesWithAI(query, allTables);
        } catch (Exception e) {
            log.warn("AI表识别失败，回退到关键词匹配: {}", e.getMessage());
            return identifyTablesWithKeywords(query, allTables);
        }
    }

    private List<TableMetadata> identifyTablesWithAI(String query, List<TableMetadata> allTables) {
        // 构建表信息摘要
        StringBuilder tableInfo = new StringBuilder();
        tableInfo.append("可用的数据表信息：\n");

        for (int i = 0; i < allTables.size(); i++) {
            TableMetadata table = allTables.get(i);
            tableInfo.append(String.format("%d. 表名：%s\n", i + 1, table.getTableName()));
            tableInfo.append(String.format("   描述：%s\n", table.getDescription()));

            // 添加主要字段信息
            tableInfo.append("   主要字段：");
            table.getColumns().stream()
                    .filter(col -> col.isPrimaryKey() || col.isSearchable() || col.isDateField())
                    .limit(8)
                    .forEach(col -> {
                        tableInfo.append(col.getColumnName()).append("(").append(col.getDescription()).append(") ");
                    });
            tableInfo.append("\n");

            // 添加常见查询示例
            if (!table.getCommonQueries().isEmpty()) {
                tableInfo.append("   常见查询：");
                table.getCommonQueries().stream().limit(3).forEach(q -> tableInfo.append(q).append("；"));
                tableInfo.append("\n");
            }
            tableInfo.append("\n");
        }

        String prompt = String.format("""
            %s
            用户查询：%s
            请分析用户查询涉及哪些数据表。判断依据：
            1. 查询内容是否与表的描述和字段匹配
            2. 查询意图是否与表的功能相符
            3. 查询关键词是否在表的常见查询中出现
            分析规则：
            - 如果查询涉及"车辆通行"、"车流量"、"车牌查询"、"车辆记录"等，选择车辆表
            - 如果查询涉及"事件"、"违法"、"事故"、"超速"、"拥堵"、"异常"等，选择事件表
            - 如果查询涉及多个方面，可以选择多个表
            请只返回相关表的编号（用逗号分隔），如：1,2
            如果不确定，返回最可能的一个表编号。
            """, tableInfo.toString(), query);

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        // 解析响应
        List<TableMetadata> identifiedTables = new ArrayList<>();
        String[] indices = response.trim().replaceAll("[^0-9,]", "").split(",");

        for (String indexStr : indices) {
            try {
                int index = Integer.parseInt(indexStr.trim()) - 1;
                if (index >= 0 && index < allTables.size()) {
                    identifiedTables.add(allTables.get(index));
                }
            } catch (NumberFormatException e) {
                // 忽略无效索引
            }
        }

        return identifiedTables.isEmpty() ? List.of(allTables.get(0)) : identifiedTables;
    }

    private List<TableMetadata> identifyTablesWithKeywords(String query, List<TableMetadata> allTables) {
        List<TableMetadata> relevantTables = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        // 车辆表关键词
        if (lowerQuery.matches(".*?(车辆|车流|车牌|通行|进出|品牌|车型|颜色).*?")) {
            TableMetadata vehicleTable = metadataManager.getTableMetadata("vp_tunnel_vehicle");
            if (vehicleTable != null) {
                relevantTables.add(vehicleTable);
            }
        }

        // 事件表关键词
        if (lowerQuery.matches(".*?(事件|违法|事故|超速|拥堵|异常|停车|逆行|变道).*?")) {
            TableMetadata eventTable = metadataManager.getTableMetadata("vp_event");
            if (eventTable != null) {
                relevantTables.add(eventTable);
            }
        }

        // 如果没有匹配到，默认返回第一个表
        if (relevantTables.isEmpty() && !allTables.isEmpty()) {
            relevantTables.add(allTables.get(0));
        }

        return relevantTables;
    }

    private String buildSchemaPrompt(List<TableMetadata> tables) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("数据库表结构信息：\n\n");

        for (TableMetadata table : tables) {
            prompt.append(String.format("表名: %s\n", table.getTableName()));
            prompt.append(String.format("表描述: %s\n", table.getDescription()));
            prompt.append("字段信息:\n");

            for (ColumnMetadata column : table.getColumns()) {
                prompt.append(String.format("- %s (%s): %s",
                        column.getColumnName(),
                        column.getDataType(),
                        column.getDescription()));

                if (column.isPrimaryKey()) {
                    prompt.append(" [主键]");
                }
                if (column.isDateField()) {
                    prompt.append(" [时间字段]");
                }
                if (column.isSearchable()) {
                    prompt.append(" [可搜索]");
                }

                // 添加枚举值信息
                if (column.getEnumValues() != null && !column.getEnumValues().isEmpty()) {
                    prompt.append(" [取值：").append(String.join("，", column.getEnumValues())).append("]");
                }

                prompt.append("\n");
            }
            prompt.append("\n");
        }

        return prompt.toString();
    }

    private String buildFullPrompt(String naturalQuery, String schemaPrompt) {
        return String.format("""
            %s
            用户查询请求: %s
            
            请根据以上表结构，将用户的自然语言查询转换为准确的SQL语句。
            
            重要要求：
            1. 只生成SQL语句，不要其他解释
            2. 使用标准的MySQL语法
            3. 对于时间查询，请使用适当的日期函数
            4. 对于模糊查询，请使用LIKE操作符
            5. 统计查询请使用COUNT等聚合函数
            6. 确保SQL语法正确且可执行
            7. 对于枚举值查询，请使用对应的数字值
            8. 如果涉及多表查询，请适当使用JOIN
            
            时间相关的查询示例：
            - 今日：DATE(字段名) = CURDATE()
            - 昨天：DATE(字段名) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)
            - 本周：WEEK(字段名) = WEEK(NOW()) AND YEAR(字段名) = YEAR(NOW())
            - 本月：MONTH(字段名) = MONTH(NOW()) AND YEAR(字段名) = YEAR(NOW())
            
            枚举值查询示例：
            - 查询轿车：vehicle_type = 1
            - 查询白色车辆：vehicle_color = 1
            - 查询新能源车：plate_type = 17
            - 查询超速事件：event_type = 6
            - 查询桑园子隧道：net_road_id = 0
            
            多表查询示例：
            - 如果需要关联车辆信息和事件信息，可以通过车牌号关联：
              SELECT * FROM vp_tunnel_vehicle v JOIN vp_event e ON v.plate_char = e.plate
            
            特殊查询处理：
            - 如果查询包含隧道名称，请转换为对应的net_road_id值
            - 如果查询包含车辆类型名称，请转换为对应的数字值
            - 如果查询包含颜色名称，请转换为对应的数字值
            
            请直接返回SQL语句：
            """, schemaPrompt, naturalQuery);
    }

    private SqlInfo parseSqlResponse(String aiResponse) {
        // 提取SQL语句（去除可能的markdown标记等）
        String sql = aiResponse.trim();
        sql = sql.replaceAll("```sql", "").replaceAll("```", "").trim();

        // 简单验证SQL格式
        if (!sql.toUpperCase().startsWith("SELECT")) {
            throw new IllegalArgumentException("生成的SQL格式不正确");
        }

        return SqlInfo.builder()
                .sql(sql)
                .queryType(determineQueryType(sql))
                .build();
    }

    private QueryType determineQueryType(String sql) {
        String upperSql = sql.toUpperCase();
        if (upperSql.contains("COUNT(") || upperSql.contains("SUM(") ||
                upperSql.contains("AVG(") || upperSql.contains("GROUP BY")) {
            return QueryType.STATISTICS;
        } else {
            return QueryType.DETAIL;
        }
    }
}

