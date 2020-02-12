package com.anita.anitamotorcycle.beans;

import java.sql.Time;
import java.util.Date;

/**
 * @author Anita
 * @description:维修记录数据对象
 * @date : 2020/1/17 15:49
 */
public class RecordBean {

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
    private String repair_status;

    //    维修人员接单
    private String repairman_id;  //维修员id
    //获取维修商名称、维修商地址、维修员名、维修员联系电话
    private String factory_name;    //（数据库该表无该字段）

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlate_numbers() {
        return plate_numbers;
    }

    public void setPlate_numbers(String plate_numbers) {
        this.plate_numbers = plate_numbers;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(String problem_type) {
        this.problem_type = problem_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepair_status() {
        return repair_status;
    }

    public void setRepair_status(String repair_status) {
        this.repair_status = repair_status;
    }

    public String getRepairman_id() {
        return repairman_id;
    }

    public void setRepairman_id(String repairman_id) {
        this.repairman_id = repairman_id;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }
}
