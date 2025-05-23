package com.vispower.ai.service;

import com.vispower.ai.domain.QueryResult;
import com.vispower.ai.domain.QueryType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueryResultFormatterService {

    private final Map<String, Map<Integer, String>> reverseEnumMappings = new HashMap<>();

    @PostConstruct
    public void initReverseEnumMappings() {
        // 车辆颜色反向映射
        Map<Integer, String> colorReverseMapping = new HashMap<>();
        colorReverseMapping.put(0, "未知");
        colorReverseMapping.put(1, "白色");
        colorReverseMapping.put(2, "灰色");
        colorReverseMapping.put(3, "黄色");
        colorReverseMapping.put(4, "粉色");
        colorReverseMapping.put(5, "红色");
        colorReverseMapping.put(6, "绿色");
        colorReverseMapping.put(7, "蓝色");
        colorReverseMapping.put(8, "棕色");
        colorReverseMapping.put(9, "黑色");
        colorReverseMapping.put(10, "紫色");
        colorReverseMapping.put(11, "桔色");
        colorReverseMapping.put(12, "青色");
        colorReverseMapping.put(13, "金色");
        colorReverseMapping.put(14, "银色");
        reverseEnumMappings.put("vehicle_color", colorReverseMapping);

        // 车辆类型反向映射
        Map<Integer, String> vehicleTypeReverseMapping = new HashMap<>();
        vehicleTypeReverseMapping.put(0, "未知");
        vehicleTypeReverseMapping.put(1, "轿车");
        vehicleTypeReverseMapping.put(2, "货车");
        vehicleTypeReverseMapping.put(3, "面包车");
        vehicleTypeReverseMapping.put(4, "客车");
        vehicleTypeReverseMapping.put(5, "小货车");
        vehicleTypeReverseMapping.put(6, "SUV");
        vehicleTypeReverseMapping.put(7, "中型客车");
        vehicleTypeReverseMapping.put(8, "摩托车");
        vehicleTypeReverseMapping.put(33, "出租车");
        reverseEnumMappings.put("vehicle_type", vehicleTypeReverseMapping);

        // 车牌类型反向映射
        Map<Integer, String> plateTypeReverseMapping = new HashMap<>();
        plateTypeReverseMapping.put(1, "蓝牌");
        plateTypeReverseMapping.put(3, "黄牌");
        plateTypeReverseMapping.put(5, "警牌");
        plateTypeReverseMapping.put(17, "新能源牌");
        plateTypeReverseMapping.put(19, "教练车牌");
        reverseEnumMappings.put("plate_type", plateTypeReverseMapping);

        // 事件类型反向映射
        Map<Integer, String> eventTypeReverseMapping = new HashMap<>();
        eventTypeReverseMapping.put(1, "逆向行驶");
        eventTypeReverseMapping.put(2, "行人入侵机动车道");
        eventTypeReverseMapping.put(3, "异常停车");
        eventTypeReverseMapping.put(4, "路段拥堵");
        eventTypeReverseMapping.put(5, "交通事故");
        eventTypeReverseMapping.put(6, "违法超速");
        eventTypeReverseMapping.put(7, "急加速");
        eventTypeReverseMapping.put(8, "急减速");
        eventTypeReverseMapping.put(9, "违规变道");
        eventTypeReverseMapping.put(10, "抛洒物");
        eventTypeReverseMapping.put(11, "危化品车辆");
        eventTypeReverseMapping.put(12, "动物");
        reverseEnumMappings.put("event_type", eventTypeReverseMapping);
        reverseEnumMappings.put("type", eventTypeReverseMapping);

        // 隧道反向映射
        Map<Integer, String> tunnelReverseMapping = new HashMap<>();
        tunnelReverseMapping.put(0, "桑园子隧道");
        tunnelReverseMapping.put(1, "冰草湾隧道");
        tunnelReverseMapping.put(2, "梨园一号隧道");
        tunnelReverseMapping.put(3, "梨园二号隧道");
        tunnelReverseMapping.put(4, "梨园三号隧道");
        tunnelReverseMapping.put(5, "梨园四号隧道");
        tunnelReverseMapping.put(6, "梨园五号隧道");
        tunnelReverseMapping.put(7, "水阜一号隧道");
        tunnelReverseMapping.put(8, "水阜二号隧道");
        tunnelReverseMapping.put(9, "忠和隧道");
        tunnelReverseMapping.put(10, "清傅主线");
        reverseEnumMappings.put("net_road_id", tunnelReverseMapping);

        // 状态映射
        Map<Integer, String> statusReverseMapping = new HashMap<>();
        statusReverseMapping.put(0, "驶入");
        statusReverseMapping.put(1, "驶出");
        reverseEnumMappings.put("status", statusReverseMapping);

        // 事件状态映射
        Map<Integer, String> eventStatusReverseMapping = new HashMap<>();
        eventStatusReverseMapping.put(0, "无");
        eventStatusReverseMapping.put(1, "已确认");
        eventStatusReverseMapping.put(2, "已撤销");
        reverseEnumMappings.put("event_status", eventStatusReverseMapping);

        // 事件等级映射
        Map<Integer, String> eventLevelReverseMapping = new HashMap<>();
        eventLevelReverseMapping.put(1, "重要");
        eventLevelReverseMapping.put(2, "一般");
        reverseEnumMappings.put("event_level", eventLevelReverseMapping);
    }

    public String formatQueryResult(QueryResult result) {
        if (result.getData().isEmpty()) {
            return "未找到相关数据";
        }

        if (result.getQueryType() == QueryType.STATISTICS) {
            return formatStatisticsResult(result.getData());
        } else {
            return formatDetailResult(result.getData());
        }
    }

    private String formatStatisticsResult(List<Map<String, Object>> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("📊 统计结果：\n\n");

        for (Map<String, Object> row : data) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                // 格式化显示名
                String displayKey = formatColumnName(key);
                String displayValue = formatColumnValue(key, value);

                sb.append(String.format("• %s: %s\n", displayKey, displayValue));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatDetailResult(List<Map<String, Object>> data) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("🔍 查询结果（共%d条记录）：\n\n", data.size()));

        int count = 0;
        for (Map<String, Object> row : data) {
            if (count >= 10) {
                sb.append("...(显示前10条记录)\n");
                break;
            }

            sb.append(String.format("📄 记录%d:\n", ++count));

            // 按重要性排序显示字段
            List<Map.Entry<String, Object>> sortedEntries = row.entrySet().stream()
                    .filter(entry -> entry.getValue() != null)
                    .sorted((e1, e2) -> getFieldPriority(e1.getKey()) - getFieldPriority(e2.getKey()))
                    .collect(Collectors.toList());

            for (Map.Entry<String, Object> entry : sortedEntries) {
                String key = entry.getKey();
                Object value = entry.getValue();

                String displayKey = formatColumnName(key);
                String displayValue = formatColumnValue(key, value);

                sb.append(String.format("  %s: %s\n", displayKey, displayValue));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatColumnName(String columnName) {
        // 字段名称美化
        Map<String, String> columnDisplayNames = new HashMap<>();
        columnDisplayNames.put("plate_char", "车牌号");
        columnDisplayNames.put("vehicle_brand", "车辆品牌");
        columnDisplayNames.put("vehicle_type", "车辆类型");
        columnDisplayNames.put("vehicle_color", "车身颜色");
        columnDisplayNames.put("plate_type", "车牌类型");
        columnDisplayNames.put("entry_time", "驶入时间");
        columnDisplayNames.put("out_time", "驶出时间");
        columnDisplayNames.put("event_name", "事件名称");
        columnDisplayNames.put("event_type", "事件类型");
        columnDisplayNames.put("start_time", "发生时间");
        columnDisplayNames.put("net_road_id", "隧道位置");
        columnDisplayNames.put("avg_speed", "平均速度");
        columnDisplayNames.put("status", "状态");
        columnDisplayNames.put("event_level", "事件等级");
        columnDisplayNames.put("create_time", "创建时间");
        columnDisplayNames.put("update_time", "更新时间");
        columnDisplayNames.put("camera", "摄像头");
        columnDisplayNames.put("lane", "车道号");
        columnDisplayNames.put("device_code", "设备编号");
        columnDisplayNames.put("stake_number", "桩号");
        columnDisplayNames.put("longitude", "经度");
        columnDisplayNames.put("latitude", "纬度");

        return columnDisplayNames.getOrDefault(columnName, columnName);
    }

    private String formatColumnValue(String columnName, Object value) {
        if (value == null) {
            return "无";
        }

        // 尝试从枚举映射中获取显示值
        if (value instanceof Number) {
            Map<Integer, String> mapping = reverseEnumMappings.get(columnName);
            if (mapping != null) {
                String displayValue = mapping.get(((Number) value).intValue());
                if (displayValue != null) {
                    return displayValue;
                }
            }
        }

        // 特殊字段格式化
        if (columnName.contains("time") && value instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
        }

        if (columnName.equals("avg_speed") && value instanceof Number) {
            return String.format("%.1f km/h", ((Number) value).doubleValue());
        }

        if (columnName.contains("img") || columnName.contains("url")) {
            return "[图片/视频链接]";
        }

        return value.toString();
    }

    private int getFieldPriority(String fieldName) {
        // 定义字段显示优先级，数字越小优先级越高
        Map<String, Integer> priorities = new HashMap<>();
        priorities.put("plate_char", 1);
        priorities.put("vehicle_brand", 2);
        priorities.put("vehicle_type", 3);
        priorities.put("vehicle_color", 4);
        priorities.put("entry_time", 5);
        priorities.put("out_time", 6);
        priorities.put("event_name", 1);
        priorities.put("event_type", 2);
        priorities.put("start_time", 3);
        priorities.put("net_road_id", 4);
        priorities.put("status", 7);
        priorities.put("avg_speed", 8);
        priorities.put("event_level", 9);
        priorities.put("camera", 10);
        priorities.put("lane", 11);

        return priorities.getOrDefault(fieldName, 99);
    }
}