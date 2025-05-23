package com.vispower.ai.service;

import com.vispower.ai.domain.QueryAnalysisResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QueryDecompositionService {

    private final ChatClient chatClient;

    public QueryDecompositionService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 分析查询是否为复合查询，如果是则分解为多个子查询
     */
    public QueryAnalysisResult analyzeQuery(String naturalQuery) {
        try {
            String analysisPrompt = buildAnalysisPrompt(naturalQuery);

            String aiResponse = chatClient.prompt()
                    .user(analysisPrompt)
                    .call()
                    .content();

            return parseAnalysisResponse(aiResponse, naturalQuery);

        } catch (Exception e) {
            log.warn("查询分析失败，使用单查询模式: {}", e.getMessage());
            // 分析失败时，回退到单查询模式
            return QueryAnalysisResult.singleQuery(naturalQuery);
        }
    }

    private String buildAnalysisPrompt(String naturalQuery) {
        return String.format("""
            请分析以下用户查询是否包含多个独立的问题：
            
            用户查询：%s
            
            分析规则：
            1. 如果查询包含"和"、"还有"、"以及"、"同时"等连接词，且连接的是不同类型的问题，则为复合查询
            2. 如果查询询问不同的统计指标或不同的数据维度，则可能是复合查询
            3. 如果查询中的问题可以独立回答且逻辑上不相关，则为复合查询
            
            请按以下格式回答：
            类型：[单查询/复合查询]
            
            如果是复合查询，请按以下格式列出子查询：
            子查询1：[第一个独立的问题]
            子查询2：[第二个独立的问题]
            子查询3：[第三个独立的问题]（如果有的话）
            
            如果是单查询，只需回答：
            类型：单查询
            
            示例：
            用户查询：今天的车流量是多少，最近的交通事故在哪里发生？
            类型：复合查询
            子查询1：今天的车流量是多少
            子查询2：最近的交通事故在哪里发生
            """, naturalQuery);
    }

    private QueryAnalysisResult parseAnalysisResponse(String response, String originalQuery) {
        String[] lines = response.trim().split("\n");

        boolean isMultiQuery = false;
        List<String> subQueries = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("类型：")) {
                isMultiQuery = line.contains("复合查询");
            } else if (line.startsWith("子查询")) {
                String[] parts = line.split("：", 2);
                if (parts.length == 2) {
                    subQueries.add(parts[1].trim());
                }
            }
        }

        if (isMultiQuery && !subQueries.isEmpty()) {
            log.info("检测到复合查询，分解为{}个子查询", subQueries.size());
            return QueryAnalysisResult.multiQuery(subQueries);
        } else {
            return QueryAnalysisResult.singleQuery(originalQuery);
        }
    }
}