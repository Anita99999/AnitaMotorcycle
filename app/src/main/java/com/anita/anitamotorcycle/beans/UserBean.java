package com.anita.anitamotorcycle.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/4 14:12
 */
public class UserBean implements Serializable {

    public UserBean(String phone, String password) {
        this.phone = phone;
        this.password = password;
        // 获取当前北京时间,设置创建时间和更新时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String currentDate = sdf.format(date);// new Date()为获取当前系统时间
        this.create_at = currentDate;
        this.update_at = currentDate;
    }

    public UserBean(String phone) {
        this.phone = phone;
    }

    public UserBean(String phone, int status) {
        this.phone = phone;
        this.status = status;
    }

    public UserBean(String phone, String oldPassword, String Password) {
        this.phone = phone;
        this.password = oldPassword;
        this.name = Password;
        // 获取当前北京时间,设置创建时间和更新时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String currentDate = sdf.format(date);// new Date()为获取当前系统时间
        this.update_at = currentDate;
    }


    private String id;
    private String phone;
    private String password;
    private String name;
    private String sex;
    private String address;
    private String url;
    private String create_at;
    private String update_at;
    private int status; //0delete, 1normal

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "User [id=" + id + ", phone=" + phone + ", password=" + password + ", name=" + name + ", sex=" + sex
                + ", address=" + address + ", url=" + url + ", create_at=" + create_at + ", update_at=" + update_at
                + ", status=" + status + "]";
    }
}
