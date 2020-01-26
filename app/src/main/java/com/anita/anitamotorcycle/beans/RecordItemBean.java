package com.anita.anitamotorcycle.beans;

import java.sql.Time;
import java.util.Date;

/**
 * @author Anita
 * @description:维修记录数据对象
 * @date : 2020/1/17 15:49
 */
public class RecordItemBean {

    public int status; // 0delete, 1normal

    public String updateAt;   //当前状态时间

//    维修申请
    public String username;  //维修申请人姓名
    public String phone;    //手机号
    public String plateNumbers;     //车牌号
    public String position;//摩托车定位
    public String troubleType;  //故障类型
    public String description;//故障描述

//    申请提交
    public Time createAt;   //维修申请创建提交时间
    public int userId;      //用户id（获取用户绑定手机号？）
    public int id;
    //维修中状态：提交成功（返回用户信息）；分派维修员中（）；维修中；维修完成；
    // 维修完成状态：维修成功；维修取消；维修失败
    public String repairStatus;

//    维修人员接单
    public int engineerId;  //维修员id
    //获取维修商名称、维修商地址、维修员名、维修员联系电话
    public String factoryName;



    public String getPlateNumbers() {
        return plateNumbers;
    }

}
