package com.vispower.ai.controller;


import com.vispower.ai.config.TableMetadataManager;
import com.vispower.ai.domain.DatabaseQAResponse;
import com.vispower.ai.service.EnhancedSmartDatabaseQAService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/smart-query")
@Slf4j
@CrossOrigin(origins = "*")
public class EnhancedSmartQueryController {

    private final EnhancedSmartDatabaseQAService enhancedQaService;
    private final TableMetadataManager metadataManager;

    public EnhancedSmartQueryController(EnhancedSmartDatabaseQAService enhancedQaService,
                                        TableMetadataManager metadataManager) {
        this.enhancedQaService = enhancedQaService;
        this.metadataManager = metadataManager;
    }

    /**
     * 增强的智能问答接口（支持复合查询）
     */
    @PostMapping("/ask")
    public ResponseEntity<DatabaseQAResponse> askQuestion(@RequestBody QueryRequest request) {
        try {
            if (request.getQuery()== null || request.getQuery().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(DatabaseQAResponse.error("查询内容不能为空"));
            }

            log.info("收到查询请求: {}", request.getQuery());
            long startTime = System.currentTimeMillis();

            DatabaseQAResponse response = enhancedQaService.query(request.getQuery().trim());

            long duration = System.currentTimeMillis() - startTime;
            log.info("查询完成，耗时: {}ms", duration);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("处理智能查询请求失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DatabaseQAResponse.error("系统内部错误: " + e.getMessage()));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QueryRequest {
        private String query;
    }
}
