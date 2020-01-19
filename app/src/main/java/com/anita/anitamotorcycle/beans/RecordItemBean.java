package com.anita.anitamotorcycle.beans;

import java.sql.Time;
import java.util.Date;

/**
 * @author Anita
 * @description:维修记录数据对象
 * @date : 2020/1/17 15:49
 */
public class RecordItemBean {
//    维修记录
    public int id;
    public int engineerId;  //维修员id
    public int userId;      //用户id
    public Time createAt;
    public int status; // 0delete, 1normal

    //维修中状态：提交成功（返回用户信息）；分派维修员中（）；维修中；维修完成；
    // 维修完成状态：维修成功；维修取消；维修失败
    public String repairStatus;
    public String plateNumbers;     //车牌号
    public String updateAt;   //当前状态时间
    public String factoryName;  //维修商名称
    public String troubleType;  //故障类型
//    维修详情
    public String username;  //维修申请人姓名
    public String phone;    //手机号

    public String position;//摩托车定位
    public String description;//故障描述

}
