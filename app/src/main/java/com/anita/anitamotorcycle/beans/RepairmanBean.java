package com.anita.anitamotorcycle.beans;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/6 15:42
 */
public class RepairmanBean {
    private String id;
    private String factory_id;
    private String phone;
    private String password;
    private String name;
    private String sex;
    private int today_do;   //今日受理
    private int today_did;   //今日办理
    private int total_did;   //今日累计已办
    private String create_at;
    private String update_at;
    private int status; // 0delete, 1normal

    public RepairmanBean(String phone) {
        this.phone = phone;
    }

    public RepairmanBean(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public RepairmanBean(String phone, int status) {
        this.phone = phone;
        this.status = status;
    }

    public int getToday_do() {
        return today_do;
    }

    public void setToday_do(int today_do) {
        this.today_do = today_do;
    }

    public int getToday_did() {
        return today_did;
    }

    public void setToday_did(int today_did) {
        this.today_did = today_did;
    }

    public int getTotal_did() {
        return total_did;
    }

    public void setTotal_did(int total_did) {
        this.total_did = total_did;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

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
}
