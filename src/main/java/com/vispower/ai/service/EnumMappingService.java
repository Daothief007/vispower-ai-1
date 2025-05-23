package com.vispower.ai.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EnumMappingService {

    private final Map<String, Map<String, Integer>> enumMappings = new HashMap<>();

    @PostConstruct
    public void initEnumMappings() {
        // 车辆颜色映射
        Map<String, Integer> colorMapping = new HashMap<>();
        colorMapping.put("未知", 0);
        colorMapping.put("白色", 1);
        colorMapping.put("灰色", 2);
        colorMapping.put("银色", 2);
        colorMapping.put("黄色", 3);
        colorMapping.put("粉色", 4);
        colorMapping.put("红色", 5);
        colorMapping.put("绿色", 6);
        colorMapping.put("蓝色", 7);
        colorMapping.put("棕色", 8);
        colorMapping.put("黑色", 9);
        colorMapping.put("紫色", 10);
        colorMapping.put("桔色", 11);
        colorMapping.put("橙色", 11);
        colorMapping.put("青色", 12);
        colorMapping.put("金色", 13);
        enumMappings.put("vehicle_color", colorMapping);

        // 车辆类型映射
        Map<String, Integer> vehicleTypeMapping = new HashMap<>();
        vehicleTypeMapping.put("未知", 0);
        vehicleTypeMapping.put("轿车", 1);
        vehicleTypeMapping.put("货车", 2);
        vehicleTypeMapping.put("面包车", 3);
        vehicleTypeMapping.put("客车", 4);
        vehicleTypeMapping.put("小货车", 5);
        vehicleTypeMapping.put("suv", 6);
        vehicleTypeMapping.put("中型客车", 7);
        vehicleTypeMapping.put("摩托车", 8);
        vehicleTypeMapping.put("行人", 9);
        vehicleTypeMapping.put("校车", 10);
        vehicleTypeMapping.put("泥头车", 11);
        vehicleTypeMapping.put("渣土车", 11);
        vehicleTypeMapping.put("高危车", 12);
        vehicleTypeMapping.put("骑行人", 13);
        vehicleTypeMapping.put("微型轿车", 14);
        vehicleTypeMapping.put("小型轿车", 15);
        vehicleTypeMapping.put("紧凑型轿车", 16);
        vehicleTypeMapping.put("两厢轿车", 17);
        vehicleTypeMapping.put("三厢轿车", 18);
        vehicleTypeMapping.put("轻客", 19);
        vehicleTypeMapping.put("小型suv", 20);
        vehicleTypeMapping.put("紧凑型suv", 21);
        vehicleTypeMapping.put("中型suv", 22);
        vehicleTypeMapping.put("中大型suv", 23);
        vehicleTypeMapping.put("大型suv", 24);
        vehicleTypeMapping.put("微型面包车", 25);
        vehicleTypeMapping.put("mpv", 26);
        vehicleTypeMapping.put("轿跑", 27);
        vehicleTypeMapping.put("微卡", 28);
        vehicleTypeMapping.put("皮卡", 29);
        vehicleTypeMapping.put("中卡", 30);
        vehicleTypeMapping.put("轻卡", 31);
        vehicleTypeMapping.put("重卡", 32);
        vehicleTypeMapping.put("出租车", 33);
        vehicleTypeMapping.put("油罐车", 34);
        vehicleTypeMapping.put("吊车", 35);
        enumMappings.put("vehicle_type", vehicleTypeMapping);

        // 车牌类型映射
        Map<String, Integer> plateTypeMapping = new HashMap<>();
        plateTypeMapping.put("蓝牌", 1);
        plateTypeMapping.put("黑牌", 2);
        plateTypeMapping.put("黄牌", 3);
        plateTypeMapping.put("双层黄牌", 4);
        plateTypeMapping.put("警牌", 5);
        plateTypeMapping.put("武警", 6);
        plateTypeMapping.put("军牌", 8);
        plateTypeMapping.put("使馆牌", 10);
        plateTypeMapping.put("香港牌", 12);
        plateTypeMapping.put("澳门牌", 13);
        plateTypeMapping.put("农用车牌", 14);
        plateTypeMapping.put("厂内牌", 15);
        plateTypeMapping.put("个性化车牌", 16);
        plateTypeMapping.put("新能源牌", 17);
        plateTypeMapping.put("教练车牌", 19);
        plateTypeMapping.put("民航车牌", 20);
        plateTypeMapping.put("应急车牌", 22);
        enumMappings.put("plate_type", plateTypeMapping);

        // 事件类型映射
        Map<String, Integer> eventTypeMapping = new HashMap<>();
        eventTypeMapping.put("逆向行驶", 1);
        eventTypeMapping.put("逆行", 1);
        eventTypeMapping.put("行人入侵", 2);
        eventTypeMapping.put("行人入侵机动车道", 2);
        eventTypeMapping.put("异常停车", 3);
        eventTypeMapping.put("路段拥堵", 4);
        eventTypeMapping.put("拥堵", 4);
        eventTypeMapping.put("交通事故", 5);
        eventTypeMapping.put("事故", 5);
        eventTypeMapping.put("违法超速", 6);
        eventTypeMapping.put("超速", 6);
        eventTypeMapping.put("急加速", 7);
        eventTypeMapping.put("急减速", 8);
        eventTypeMapping.put("违规变道", 9);
        eventTypeMapping.put("变道", 9);
        eventTypeMapping.put("抛洒物", 10);
        eventTypeMapping.put("危化品车辆", 11);
        eventTypeMapping.put("危化品", 11);
        eventTypeMapping.put("动物", 12);
        enumMappings.put("event_type", eventTypeMapping);
        enumMappings.put("type", eventTypeMapping);

        // 隧道映射
        Map<String, Integer> tunnelMapping = new HashMap<>();
        tunnelMapping.put("桑园子隧道", 0);
        tunnelMapping.put("桑园子", 0);
        tunnelMapping.put("冰草湾隧道", 1);
        tunnelMapping.put("冰草湾", 1);
        tunnelMapping.put("梨园一号隧道", 2);
        tunnelMapping.put("梨园一号", 2);
        tunnelMapping.put("梨园二号隧道", 3);
        tunnelMapping.put("梨园二号", 3);
        tunnelMapping.put("梨园三号隧道", 4);
        tunnelMapping.put("梨园三号", 4);
        tunnelMapping.put("梨园四号隧道", 5);
        tunnelMapping.put("梨园四号", 5);
        tunnelMapping.put("梨园五号隧道", 6);
        tunnelMapping.put("梨园五号", 6);
        tunnelMapping.put("水阜一号隧道", 7);
        tunnelMapping.put("水阜一号", 7);
        tunnelMapping.put("水阜二号隧道", 8);
        tunnelMapping.put("水阜二号", 8);
        tunnelMapping.put("忠和隧道", 9);
        tunnelMapping.put("忠和", 9);
        tunnelMapping.put("清傅主线", 10);
        tunnelMapping.put("清傅", 10);
        enumMappings.put("net_road_id", tunnelMapping);
    }

    public String preprocessQuery(String naturalQuery) {
        String processedQuery = naturalQuery;

        // 遍历所有枚举映射，尝试替换自然语言为对应的数字值
        for (Map.Entry<String, Map<String, Integer>> fieldEntry : enumMappings.entrySet()) {
            String fieldName = fieldEntry.getKey();
            Map<String, Integer> valueMapping = fieldEntry.getValue();

            for (Map.Entry<String, Integer> valueEntry : valueMapping.entrySet()) {
                String naturalValue = valueEntry.getKey();
                Integer numericValue = valueEntry.getValue();

                // 替换自然语言描述
                if (processedQuery.contains(naturalValue)) {
                    processedQuery = processedQuery.replace(naturalValue,
                            String.format("%s=%d", fieldName, numericValue));
                }
            }
        }

        return processedQuery;
    }

    public Map<String, Integer> getEnumMapping(String fieldName) {
        return enumMappings.getOrDefault(fieldName, new HashMap<>());
    }
}