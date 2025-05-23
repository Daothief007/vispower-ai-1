package com.vispower.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableMetadata {
    private String tableName;
    private String description;
    private List<ColumnMetadata> columns;
    private List<String> commonQueries;
}
