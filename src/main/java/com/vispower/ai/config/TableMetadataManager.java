package com.vispower.ai.config;

import com.vispower.ai.domain.ColumnMetadata;
import com.vispower.ai.domain.TableMetadata;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

// ===== 2. 表结构元数据管理 =====
@Component
public class TableMetadataManager {

    private final Map<String, TableMetadata> tableMetadataMap = new HashMap<>();

    @PostConstruct
    public void initMetadata() {
        // 初始化隧道车辆表元数据
        TableMetadata tunnelVehicleMetadata = TableMetadata.builder()
                .tableName("vp_tunnel_vehicle")
                .description("隧道车辆监控记录表，记录通过隧道的所有车辆信息")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("vehicle_id")
                                .dataType("bigint")
                                .description("车辆ID")
                                .isPrimaryKey(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("vehicle_brand")
                                .dataType("varchar(50)")
                                .description("汽车品牌")
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("vehicle_type")
                                .dataType("int")
                                .description("车辆类型")
                                .enumValues(Arrays.asList(
                                        "0-未知", "1-轿车", "2-货车", "3-面包车", "4-客车", "5-小货车", "6-SUV", "7-中型客车",
                                        "8-摩托车", "9-行人", "10-校车", "11-泥头车", "12-高危车", "13-骑行人", "14-微型轿车",
                                        "15-小型轿车", "16-紧凑型轿车", "17-两厢轿车", "18-三厢轿车", "19-轻客", "20-小型SUV",
                                        "21-紧凑型SUV", "22-中型SUV", "23-中大型SUV", "24-大型SUV", "25-微型面包车", "26-MPV",
                                        "27-轿跑", "28-微卡", "29-皮卡", "30-中卡", "31-轻卡", "32-重卡", "33-出租车",
                                        "34-油罐车", "35-吊车"
                                ))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("vehicle_sub_brand")
                                .dataType("varchar(50)")
                                .description("子品牌")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("plate_char")
                                .dataType("varchar(50)")
                                .description("车牌号")
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("plate_color")
                                .dataType("int")
                                .description("车牌颜色")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("plate_type")
                                .dataType("int")
                                .description("车牌类型")
                                .enumValues(Arrays.asList(
                                        "0-未知类型", "1-单层蓝牌", "2-单层黑牌", "3-单层黄牌", "4-双层黄牌", "5-白色警牌",
                                        "6-白色武警", "7-双层白色武警", "8-单层军牌", "9-双层军牌", "10-领使馆牌",
                                        "12-香港大陆牌", "13-澳门牌", "14-农用车牌", "15-厂内牌", "16-个性化车牌",
                                        "17-新能源牌", "18-其他类型", "19-教练车牌", "20-民航车牌", "22-应急车牌",
                                        "23-非机动车双层蓝牌", "24-使馆车牌", "25-挂车车牌", "26-摩托车黄牌", "27-摩托车蓝牌"
                                ))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("vehicle_color")
                                .dataType("int")
                                .description("车身颜色")
                                .enumValues(Arrays.asList(
                                        "0-未知", "1-白色", "2-灰色(银色)", "3-黄色", "4-粉色", "5-红色", "6-绿色",
                                        "7-蓝色", "8-棕色", "9-黑色", "10-紫色", "11-桔色", "12-青色", "13-金色", "14-银色"
                                ))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("img_all")
                                .dataType("varchar(255)")
                                .description("全景车辆图片")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("img_middle")
                                .dataType("varchar(255)")
                                .description("中景车辆图片")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("img_plate")
                                .dataType("varchar(255)")
                                .description("车牌图片")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("device_code")
                                .dataType("varchar(255)")
                                .description("设备编号")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("in_stake_number")
                                .dataType("varchar(255)")
                                .description("进入桩号")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("out_stake_number")
                                .dataType("varchar(255)")
                                .description("驶出桩号")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("status")
                                .dataType("int")
                                .description("状态：0-驶入，1-驶出")
                                .enumValues(Arrays.asList("0-驶入", "1-驶出"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("direction")
                                .dataType("int")
                                .description("行驶方向")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("entry_time")
                                .dataType("datetime")
                                .description("驶入时间")
                                .isDateField(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("out_time")
                                .dataType("datetime")
                                .description("驶出时间")
                                .isDateField(true)
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "统计今日车流量",
                        "查询昨天的车辆记录",
                        "本周车流量统计",
                        "本月车辆进出记录",
                        "查询特定车牌的历史记录",
                        "按品牌统计车辆数量",
                        "按车辆类型分析",
                        "按车身颜色统计",
                        "新能源车统计"
                ))
                .build();

        // 初始化交通事件表元数据
        TableMetadata eventMetadata = TableMetadata.builder()
                .tableName("vp_event")
                .description("交通事件记录表，记录各种交通异常事件和违法行为")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("event_id")
                                .dataType("bigint")
                                .description("事件ID")
                                .isPrimaryKey(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("event_name")
                                .dataType("varchar(20)")
                                .description("事件类型名称")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("longitude")
                                .dataType("decimal(20,8)")
                                .description("经度")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("latitude")
                                .dataType("decimal(20,8)")
                                .description("纬度")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("id")
                                .dataType("int")
                                .description("车辆ID")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("lane")
                                .dataType("int")
                                .description("车道号")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("start_time")
                                .dataType("datetime")
                                .description("事件发生时间")
                                .isDateField(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("plate")
                                .dataType("varchar(50)")
                                .description("车牌")
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("camera")
                                .dataType("varchar(100)")
                                .description("摄像头编号（尾号表示上下行：1上；2下）")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("type")
                                .dataType("int")
                                .description("事件类型")
                                .enumValues(Arrays.asList(
                                        "1-逆向行驶", "2-行人入侵机动车道", "3-异常停车", "4-路段拥堵", "5-交通事故",
                                        "6-违法超速", "7-急加速", "8-急减速", "9-违规变道", "10-抛洒物",
                                        "11-危化品车辆", "12-动物"
                                ))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("event_type")
                                .dataType("int")
                                .description("事件类型")
                                .enumValues(Arrays.asList(
                                        "1-逆向行驶", "2-行人入侵机动车道", "3-异常停车", "4-路段拥堵", "5-交通事故",
                                        "6-违法超速", "7-急加速", "8-急减速", "9-违规变道", "10-抛洒物",
                                        "11-危化品车辆", "12-动物"
                                ))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("avg_speed")
                                .dataType("double(10,2)")
                                .description("平均速度")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("space_rate")
                                .dataType("double(20,8)")
                                .description("空间占有率")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("create_time")
                                .dataType("datetime")
                                .description("创建时间")
                                .isDateField(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("net_road_id")
                                .dataType("int")
                                .description("隧道路段ID")
                                .enumValues(Arrays.asList(
                                        "0-桑园子隧道", "1-冰草湾隧道", "2-梨园一号隧道", "3-梨园二号隧道", "4-梨园三号隧道",
                                        "5-梨园四号隧道", "6-梨园五号隧道", "7-水阜一号隧道", "8-水阜二号隧道", "9-忠和隧道", "10-清傅主线"
                                ))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("status")
                                .dataType("int")
                                .description("状态")
                                .enumValues(Arrays.asList("0-无", "1-已确认", "2-已撤销"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("event_level")
                                .dataType("int")
                                .description("事件等级")
                                .enumValues(Arrays.asList("1-重要", "2-一般"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("netroad_position")
                                .dataType("varchar(255)")
                                .description("隧道位置")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("stake_number")
                                .dataType("varchar(100)")
                                .description("桩号")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("event_url")
                                .dataType("varchar(255)")
                                .description("视频地址")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("update_time")
                                .dataType("datetime")
                                .description("修改时间")
                                .isDateField(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("accomplish")
                                .dataType("int")
                                .description("是否拥堵")
                                .enumValues(Arrays.asList("0-是", "1-否"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("img_url")
                                .dataType("varchar(255)")
                                .description("事件截图地址")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询今日交通事件",
                        "统计各类事件数量",
                        "查询超速违法记录",
                        "统计拥堵事件",
                        "查询交通事故",
                        "按隧道统计事件",
                        "查询未处理事件",
                        "重要事件统计",
                        "异常停车事件"
                ))
                .build();
        // 1. CO/VI检测器表
        TableMetadata coViTable = TableMetadata.builder()
                .tableName("co_vi")
                .description("一氧化碳和能见度检测器，监测隧道内空气质量")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("device_code")
                                .dataType("VARCHAR")
                                .description("设备编号")
                                .isPrimaryKey(true)
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("device_name")
                                .dataType("VARCHAR")
                                .description("设备名称")
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("co_value")
                                .dataType("DOUBLE")
                                .description("一氧化碳浓度值")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("vi_value")
                                .dataType("DOUBLE")
                                .description("能见度值")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("net_road_id")
                                .dataType("BIGINT")
                                .description("隧道ID（0-10对应不同隧道）")
                                .enumValues(Arrays.asList("0-桑园子隧道", "1-冰草湾隧道", "2-梨园一号隧道"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("comm_state")
                                .dataType("VARCHAR")
                                .description("通讯状态")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("fault_state")
                                .dataType("VARCHAR")
                                .description("故障状态")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("update_time")
                                .dataType("DATETIME")
                                .description("更新时间")
                                .isDateField(true)
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询所有CO浓度超标的设备",
                        "查询某隧道的能见度情况",
                        "查询故障的CO/VI检测器"
                ))
                .build();

        // 2. 风机表
        TableMetadata fanTable = TableMetadata.builder()
                .tableName("draught_fan")
                .description("隧道通风风机，用于隧道内空气流通")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("device_code")
                                .dataType("VARCHAR")
                                .description("设备编号")
                                .isPrimaryKey(true)
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("current_state")
                                .dataType("VARCHAR")
                                .description("当前状态（运行/停止）")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("net_road_id")
                                .dataType("BIGINT")
                                .description("隧道ID")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询所有运行中的风机",
                        "查询某隧道的风机状态",
                        "查询故障风机"
                ))
                .build();

        // 3. 紧急电话表
        TableMetadata phoneTable = TableMetadata.builder()
                .tableName("emergency_phone")
                .description("隧道紧急电话设备，用于紧急求助")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("device_code")
                                .dataType("VARCHAR")
                                .description("设备编号")
                                .isPrimaryKey(true)
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("current_state")
                                .dataType("VARCHAR")
                                .description("当前状态（erok正常/call通话中/nerr故障/pick监听/offline离线）")
                                .enumValues(Arrays.asList("erok-正常", "call-通话中", "nerr-故障", "pick-监听", "offline-离线"))
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询正在通话的紧急电话",
                        "查询离线的紧急电话",
                        "查询某隧道的紧急电话状态"
                ))
                .build();

        // 4. 火灾报警表
        TableMetadata fireAlarmTable = TableMetadata.builder()
                .tableName("fire_alarm")
                .description("消防火灾报警设备，监测火灾情况")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("is_warning")
                                .dataType("TINYINT")
                                .description("是否处于报警状态（0否/1是）")
                                .enumValues(Arrays.asList("0-正常", "1-报警"))
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询所有火灾报警设备",
                        "查询正在报警的设备",
                        "查询某隧道的火灾报警状态"
                ))
                .build();

        // 5. 情报板表
        TableMetadata intelBoardTable = TableMetadata.builder()
                .tableName("intel_board")
                .description("可变情报板，显示交通信息和警示信息")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("content")
                                .dataType("VARCHAR")
                                .description("显示内容")
                                .isSearchable(true)
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("color")
                                .dataType("VARCHAR")
                                .description("文字颜色")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询所有情报板显示内容",
                        "查询某隧道的情报板",
                        "查询故障的情报板"
                ))
                .build();

        // 6. 车道指示器表
        TableMetadata laneIndicateTable = TableMetadata.builder()
                .tableName("lane_indicate")
                .description("车道指示器，显示车道开放状态")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("front_state")
                                .dataType("VARCHAR")
                                .description("正面状态（green绿箭/red红叉/black黑屏/left左转）")
                                .enumValues(Arrays.asList("green-绿箭", "red-红叉", "black-黑屏", "left-左转"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("back_state")
                                .dataType("VARCHAR")
                                .description("背面状态")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询红叉状态的车道",
                        "查询某隧道的车道状态",
                        "查询所有绿箭车道"
                ))
                .build();

        // 7. 亮度检测器表
        TableMetadata luminousTable = TableMetadata.builder()
                .tableName("luminous_detect")
                .description("洞内外亮度检测器，监测光照强度")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("type")
                                .dataType("TINYINT")
                                .description("类型（0洞内/1洞外）")
                                .enumValues(Arrays.asList("0-洞内光强", "1-洞外光强"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("brightness")
                                .dataType("DOUBLE")
                                .description("光照强度值")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询洞外光照强度",
                        "查询洞内光照强度",
                        "查询某隧道的光照情况"
                ))
                .build();

        // 8. 感温光栅表
        TableMetadata rasterTable = TableMetadata.builder()
                .tableName("raster")
                .description("感温光栅，监测隧道温度")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("temp")
                                .dataType("DOUBLE")
                                .description("温度值")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("is_error")
                                .dataType("TINYINT")
                                .description("设备是否错误")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询温度异常的位置",
                        "查询某隧道的温度",
                        "查询故障的感温光栅"
                ))
                .build();

        // 9. 卷帘门表
        TableMetadata rollDoorTable = TableMetadata.builder()
                .tableName("roll_door")
                .description("隧道卷帘门，用于紧急封闭")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("state")
                                .dataType("VARCHAR")
                                .description("状态（up上升/down下降/stop停止/error异常/up_limit上到位/down_limit下到位）")
                                .enumValues(Arrays.asList("up-上升", "down-下降", "stop-停止", "error-异常", "up_limit-上到位", "down_limit-下到位"))
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询卷帘门状态",
                        "查询正在动作的卷帘门",
                        "查询异常的卷帘门"
                ))
                .build();

        // 10. 照明表
        TableMetadata lampTable = TableMetadata.builder()
                .tableName("sansi_lamp")
                .description("隧道照明设备")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("current_state")
                                .dataType("VARCHAR")
                                .description("状态（light_on开启/light_off关闭）")
                                .enumValues(Arrays.asList("light_on-照明开启", "light_off-照明关闭"))
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("bright")
                                .dataType("INT")
                                .description("亮度值")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("mode")
                                .dataType("INT")
                                .description("运行模式（6自动/7手动/255未知）")
                                .enumValues(Arrays.asList("6-自动模式", "7-手动模式", "255-未知模式"))
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询关闭的照明",
                        "查询某隧道的照明状态",
                        "查询手动模式的照明"
                ))
                .build();

        // 11. 烟感表
        TableMetadata smokingTable = TableMetadata.builder()
                .tableName("smoking")
                .description("烟雾感应器，检测火灾烟雾")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("is_warning")
                                .dataType("TINYINT")
                                .description("是否报警状态")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询烟感报警",
                        "查询某隧道的烟感状态"
                ))
                .build();

        // 12. 交通信号灯表
        TableMetadata trafficLightTable = TableMetadata.builder()
                .tableName("traffic_light")
                .description("隧道交通信号灯")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("state")
                                .dataType("VARCHAR")
                                .description("信号灯状态")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询交通信号灯状态",
                        "查询某隧道的信号灯"
                ))
                .build();

        // 13. 水泵表
        TableMetadata waterPumpTable = TableMetadata.builder()
                .tableName("water_pump")
                .description("隧道排水水泵")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("current_state")
                                .dataType("VARCHAR")
                                .description("状态（wapu_on启动/wapu_off停止）")
                                .enumValues(Arrays.asList("wapu_on-启动", "wapu_off-停止"))
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询运行中的水泵",
                        "查询某隧道的水泵状态"
                ))
                .build();

        // 14. 风速风向表
        TableMetadata windDetectorTable = TableMetadata.builder()
                .tableName("wind_detector")
                .description("风速风向检测器，监测隧道内风速风向")
                .columns(Arrays.asList(
                        ColumnMetadata.builder()
                                .columnName("wind_speed")
                                .dataType("DOUBLE")
                                .description("风速")
                                .build(),
                        ColumnMetadata.builder()
                                .columnName("wind_direction")
                                .dataType("VARCHAR")
                                .description("风向")
                                .build()
                ))
                .commonQueries(Arrays.asList(
                        "查询风速情况",
                        "查询某隧道的风向"
                ))
                .build();

        // 将所有表添加到管理器
        tableMetadataMap.put("co_vi", coViTable);
        tableMetadataMap.put("draught_fan", fanTable);
        tableMetadataMap.put("emergency_phone", phoneTable);
        tableMetadataMap.put("fire_alarm", fireAlarmTable);
        tableMetadataMap.put("intel_board", intelBoardTable);
        tableMetadataMap.put("lane_indicate", laneIndicateTable);
        tableMetadataMap.put("luminous_detect", luminousTable);
        tableMetadataMap.put("raster", rasterTable);
        tableMetadataMap.put("roll_door", rollDoorTable);
        tableMetadataMap.put("sansi_lamp", lampTable);
        tableMetadataMap.put("smoking", smokingTable);
        tableMetadataMap.put("traffic_light", trafficLightTable);
        tableMetadataMap.put("water_pump", waterPumpTable);
        tableMetadataMap.put("wind_detector", windDetectorTable);
        tableMetadataMap.put("vp_tunnel_vehicle", tunnelVehicleMetadata);
        tableMetadataMap.put("vp_event", eventMetadata);
    }

    public TableMetadata getTableMetadata(String tableName) {
        return tableMetadataMap.get(tableName);
    }

    public List<TableMetadata> getAllTableMetadata() {
        return new ArrayList<>(tableMetadataMap.values());
    }

    public void addTableMetadata(TableMetadata metadata) {
        tableMetadataMap.put(metadata.getTableName(), metadata);
    }
}