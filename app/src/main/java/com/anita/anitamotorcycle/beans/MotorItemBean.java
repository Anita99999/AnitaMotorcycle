package com.anita.anitamotorcycle.beans;


import java.sql.Time;

/**
 * @author Anita
 * @description:我的摩托車 * @date : 2020/1/18 21:32
 */
public class MotorItemBean {
    public int id;
    //    添加摩托车
    public String code;// 车驾号
    public String factory;//制造商
    public String country;  //生产国家
    public String model;//型号
    public String type; //类型
    public int year;//生产年份
    public int number;//生产顺序号
    public String plateNumbers; // 车牌号

//    摩托车信息
    public String url;//图片

    public String getPlateNumbers() {
        return plateNumbers;
    }

    public int warrantyTime;//保修期
    public int warrantyDistance;//保修公里

    public Time createAt;
    public Time updateAt;
    public int status; // 0delete, 1normal
}
