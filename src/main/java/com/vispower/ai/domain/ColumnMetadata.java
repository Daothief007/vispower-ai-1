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
public class ColumnMetadata {
    private String columnName;
    private String dataType;
    private String description;
    private boolean isPrimaryKey;
    private boolean isSearchable;
    private boolean isDateField;
    private List<String> enumValues;
}