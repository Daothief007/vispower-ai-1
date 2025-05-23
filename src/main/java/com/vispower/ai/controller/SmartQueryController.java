package com.vispower.ai.controller;

import com.vispower.ai.domain.DatabaseQAResponse;
import com.vispower.ai.service.SmartDatabaseQAService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class SmartQueryController {

    private final SmartDatabaseQAService qaService;

    public SmartQueryController(SmartDatabaseQAService qaService) {
        this.qaService = qaService;
    }

    @PostMapping("/ask")
    public ResponseEntity<DatabaseQAResponse> askQuestion(@RequestBody QueryRequest request) {
        try {
            DatabaseQAResponse response = qaService.query(request.getQuery());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("处理智能查询请求失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DatabaseQAResponse.error("系统内部错误"));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QueryRequest {
        private String query;
    }
}