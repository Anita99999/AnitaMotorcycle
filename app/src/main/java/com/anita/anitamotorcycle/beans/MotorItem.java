package com.anita.anitamotorcycle.beans;


import java.sql.Time;

/**
 * @author Anita
 * @description:我的摩托車 * @date : 2020/1/18 21:32
 */
public class MotorItem {
    private String id;
    private String user_id;

    //    添加摩托车
    private String vin_code;// 车驾号

    private String brand;//制造商
    private String model;//车辆型号
    private String type; //车辆类型
    private String country;  //生产国家
    private int year;//生产年份
    private int number;//生产顺序号（数据库无该字段）
    private String plate_numbers; // 车牌号

//    摩托车信息
    private String url;//图片

    private String buy_at;//购买日期（计算保修期）
    private int warranty_distance;//保修公里

//    统计
    private int today_runtime;// 今日骑行分钟数
    private int today_distance; //今日骑行公里数
    private int total_distance; //总骑行公里数

    private Time create_at;
    private Time update_at;
    private int status; // 0delete, 1normal


}
