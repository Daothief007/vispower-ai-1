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

    private final Map<String, Map<String, String>> stringEnumMappings = new HashMap<>();

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

    private void initElectromechanicalIntegerMappings() {
        // 设备通讯状态映射 (转换为数字)
        Map<String, Integer> commStateMapping = new HashMap<>();
        commStateMapping.put("正常", 0);
        commStateMapping.put("通讯正常", 0);
        commStateMapping.put("异常", 1);
        commStateMapping.put("通讯异常", 1);
        commStateMapping.put("中断", 2);
        commStateMapping.put("离线", 3);
        enumMappings.put("comm_state", commStateMapping);

        // 设备故障状态映射
        Map<String, Integer> faultStateMapping = new HashMap<>();
        faultStateMapping.put("正常", 0);
        faultStateMapping.put("无故障", 0);
        faultStateMapping.put("故障", 1);
        faultStateMapping.put("有故障", 1);
        faultStateMapping.put("警告", 2);
        enumMappings.put("fault_state", faultStateMapping);

        // 照明模式映射
        Map<String, Integer> lampModeMapping = new HashMap<>();
        lampModeMapping.put("自动", 6);
        lampModeMapping.put("自动模式", 6);
        lampModeMapping.put("手动", 7);
        lampModeMapping.put("手动模式", 7);
        lampModeMapping.put("未知", 255);
        lampModeMapping.put("未知模式", 255);
        enumMappings.put("mode", lampModeMapping);

        // 光强类型映射
        Map<String, Integer> luminousTypeMapping = new HashMap<>();
        luminousTypeMapping.put("洞内", 0);
        luminousTypeMapping.put("洞内光强", 0);
        luminousTypeMapping.put("隧道内", 0);
        luminousTypeMapping.put("洞外", 1);
        luminousTypeMapping.put("洞外光强", 1);
        luminousTypeMapping.put("隧道外", 1);
        enumMappings.put("type", luminousTypeMapping);

        // 车道号映射
        Map<String, Integer> lineMapping = new HashMap<>();
        lineMapping.put("左侧", 1);
        lineMapping.put("车道左侧", 1);
        lineMapping.put("左边", 1);
        lineMapping.put("右侧", 2);
        lineMapping.put("车道右侧", 2);
        lineMapping.put("右边", 2);
        lineMapping.put("中间", 3);
        lineMapping.put("车道中间", 3);
        lineMapping.put("中央", 3);
        enumMappings.put("line", lineMapping);

        // 报警状态映射
        Map<String, Integer> warningMapping = new HashMap<>();
        warningMapping.put("正常", 0);
        warningMapping.put("无报警", 0);
        warningMapping.put("报警", 1);
        warningMapping.put("报警中", 1);
        warningMapping.put("有报警", 1);
        enumMappings.put("is_warning", warningMapping);
    }

    private void initElectromechanicalStringMappings() {
        // 紧急电话状态映射
        Map<String, String> phoneStateMapping = new HashMap<>();
        phoneStateMapping.put("正常", "erok");
        phoneStateMapping.put("通话中", "call");
        phoneStateMapping.put("广播中", "call");
        phoneStateMapping.put("故障", "nerr");
        phoneStateMapping.put("开门报警", "nerr");
        phoneStateMapping.put("功放故障", "nerr");
        phoneStateMapping.put("监听", "pick");
        phoneStateMapping.put("离线", "offline");
        phoneStateMapping.put("中断", "offline");
        stringEnumMappings.put("current_state", phoneStateMapping);

        // 车道指示器状态映射
        Map<String, String> laneStateMapping = new HashMap<>();
        laneStateMapping.put("绿箭", "green");
        laneStateMapping.put("通行", "green");
        laneStateMapping.put("红叉", "red");
        laneStateMapping.put("禁行", "red");
        laneStateMapping.put("黑屏", "black");
        laneStateMapping.put("关闭", "black");
        laneStateMapping.put("左转", "left");
        stringEnumMappings.put("front_state", laneStateMapping);
        stringEnumMappings.put("back_state", laneStateMapping);

        // 卷帘门状态映射
        Map<String, String> rollDoorStateMapping = new HashMap<>();
        rollDoorStateMapping.put("上升", "up");
        rollDoorStateMapping.put("正在上升", "up");
        rollDoorStateMapping.put("下降", "down");
        rollDoorStateMapping.put("正在下降", "down");
        rollDoorStateMapping.put("停止", "stop");
        rollDoorStateMapping.put("异常", "error");
        rollDoorStateMapping.put("上到位", "up_limit");
        rollDoorStateMapping.put("完全打开", "up_limit");
        rollDoorStateMapping.put("下到位", "down_limit");
        rollDoorStateMapping.put("完全关闭", "down_limit");
        stringEnumMappings.put("state", rollDoorStateMapping);

        // 照明状态映射
        Map<String, String> lampStateMapping = new HashMap<>();
        lampStateMapping.put("开启", "light_on");
        lampStateMapping.put("照明开启", "light_on");
        lampStateMapping.put("开", "light_on");
        lampStateMapping.put("关闭", "light_off");
        lampStateMapping.put("照明关闭", "light_off");
        lampStateMapping.put("关", "light_off");
        // 针对照明的 current_state 字段，需要单独处理
        stringEnumMappings.put("lamp_current_state", lampStateMapping);

        // 水泵状态映射
        Map<String, String> waterPumpStateMapping = new HashMap<>();
        waterPumpStateMapping.put("启动", "wapu_on");
        waterPumpStateMapping.put("运行", "wapu_on");
        waterPumpStateMapping.put("开启", "wapu_on");
        waterPumpStateMapping.put("停止", "wapu_off");
        waterPumpStateMapping.put("关闭", "wapu_off");
        // 针对水泵的 current_state 字段
        stringEnumMappings.put("pump_current_state", waterPumpStateMapping);
    }

    // 修改预处理方法，同时处理 Integer 和 String 类型的映射
    public String preprocessQuery(String naturalQuery) {
        String processedQuery = naturalQuery;

        // 处理 Integer 类型的枚举映射
        for (Map.Entry<String, Map<String, Integer>> fieldEntry : enumMappings.entrySet()) {
            String fieldName = fieldEntry.getKey();
            Map<String, Integer> valueMapping = fieldEntry.getValue();

            for (Map.Entry<String, Integer> valueEntry : valueMapping.entrySet()) {
                String naturalValue = valueEntry.getKey();
                Integer numericValue = valueEntry.getValue();

                if (processedQuery.contains(naturalValue)) {
                    // 特殊处理：如果是查询语句，需要更智能的替换
                    processedQuery = processedQuery.replace(naturalValue,
                            String.format("%s=%d", fieldName, numericValue));
                }
            }
        }

        // 处理 String 类型的枚举映射
        for (Map.Entry<String, Map<String, String>> fieldEntry : stringEnumMappings.entrySet()) {
            String fieldName = fieldEntry.getKey();
            Map<String, String> valueMapping = fieldEntry.getValue();

            for (Map.Entry<String, String> valueEntry : valueMapping.entrySet()) {
                String naturalValue = valueEntry.getKey();
                String codeValue = valueEntry.getValue();

                if (processedQuery.contains(naturalValue)) {
                    // 对于字符串值，需要加引号
                    processedQuery = processedQuery.replace(naturalValue,
                            String.format("%s='%s'", fieldName, codeValue));
                }
            }
        }

        return processedQuery;
    }

    // 获取 Integer 类型的枚举映射
    public Map<String, Integer> getEnumMapping(String fieldName) {
        return enumMappings.getOrDefault(fieldName, new HashMap<>());
    }

    // 新增：获取 String 类型的枚举映射
    public Map<String, String> getStringEnumMapping(String fieldName) {
        return stringEnumMappings.getOrDefault(fieldName, new HashMap<>());
    }

    // 新增：智能获取枚举值（自动判断类型）
    public Object getEnumValue(String fieldName, String naturalValue) {
        // 先尝试从 Integer 映射中查找
        Map<String, Integer> intMapping = enumMappings.get(fieldName);
        if (intMapping != null && intMapping.containsKey(naturalValue)) {
            return intMapping.get(naturalValue);
        }

        // 再尝试从 String 映射中查找
        Map<String, String> stringMapping = stringEnumMappings.get(fieldName);
        if (stringMapping != null && stringMapping.containsKey(naturalValue)) {
            return stringMapping.get(naturalValue);
        }

        // 特殊处理：根据表名+字段名组合查找
        String tableFieldKey = getTableSpecificFieldKey(fieldName);
        if (tableFieldKey != null) {
            stringMapping = stringEnumMappings.get(tableFieldKey);
            if (stringMapping != null && stringMapping.containsKey(naturalValue)) {
                return stringMapping.get(naturalValue);
            }
        }

        return null;
    }

    // 根据上下文获取表特定的字段键
    private String getTableSpecificFieldKey(String fieldName) {
        if ("current_state".equals(fieldName)) {
            // 这里需要根据查询上下文判断是哪个表的 current_state
            // 可能需要传入额外的上下文信息
            return null; // 简化处理，实际使用时需要更复杂的逻辑
        }
        return null;
    }
}