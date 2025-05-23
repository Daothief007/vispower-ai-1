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