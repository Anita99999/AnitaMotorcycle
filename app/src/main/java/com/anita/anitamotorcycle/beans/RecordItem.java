package com.anita.anitamotorcycle.beans;

import java.sql.Time;
import java.util.Date;

/**
 * @author Anita
 * @description:维修记录数据对象
 * @date : 2020/1/17 15:49
 */
public class RecordItem {

    private String update_at;   //当前状态时间
    private int status; // 0delete, 1normal

    //    维修申请
    private String user_name;  //维修申请人姓名
    private String phone;    //手机号
    private String plate_numbers;     //车牌号
    private String position;//摩托车定位
    private String problem_type;  //故障类型
    private String description;//故障描述

    //    申请提交
    private String create_at;   //维修申请创建提交时间
    private String user_id;      //用户id（获取用户绑定手机号？）
    private String id;
    //维修中状态：提交成功（返回用户信息）；分派维修员中（）；维修中；维修完成；
    // 维修完成状态：维修成功；维修取消；维修失败
    private int repair_status;

    //    维修人员接单
    private String repairman_id;  //维修员id
    //获取维修商名称、维修商地址、维修员名、维修员联系电话
    private String factory_name;    //（数据库该表无该字段）


}
