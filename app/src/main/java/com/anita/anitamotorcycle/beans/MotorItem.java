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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVin_code() {
        return vin_code;
    }

    public void setVin_code(String vin_code) {
        this.vin_code = vin_code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPlate_numbers() {
        return plate_numbers;
    }

    public void setPlate_numbers(String plate_numbers) {
        this.plate_numbers = plate_numbers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBuy_at() {
        return buy_at;
    }

    public void setBuy_at(String buy_at) {
        this.buy_at = buy_at;
    }

    public int getWarranty_distance() {
        return warranty_distance;
    }

    public void setWarranty_distance(int warranty_distance) {
        this.warranty_distance = warranty_distance;
    }

    public int getToday_runtime() {
        return today_runtime;
    }

    public void setToday_runtime(int today_runtime) {
        this.today_runtime = today_runtime;
    }

    public int getToday_distance() {
        return today_distance;
    }

    public void setToday_distance(int today_distance) {
        this.today_distance = today_distance;
    }

    public int getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(int total_distance) {
        this.total_distance = total_distance;
    }

    public Time getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Time create_at) {
        this.create_at = create_at;
    }

    public Time getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Time update_at) {
        this.update_at = update_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
