package com.anita.anitamotorcycle.helps;

import com.anita.anitamotorcycle.beans.MotorItem;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/9 10:40
 */
public class MotorHelper {

    //    制订为全局的单例模式
    private static MotorHelper instance;
    private MotorHelper() {};
    public static MotorHelper getInstance() {
        if (instance == null) {
            synchronized (MotorHelper.class) {
                if (instance == null) {
                    instance = new MotorHelper();
                }
            }
        }
        return instance;
    }

    private String currentMotorId;  //页面显示车辆
    private MotorItem MotorItem; //添加车辆

    public String getCurrentMotorId() {
        return currentMotorId;
    }

    public void setCurrentMotorId(String currentMotorId) {
        this.currentMotorId = currentMotorId;
    }

    public MotorItem getMotorItem() {
        return MotorItem;
    }

    public void setMotorItem(MotorItem motorItem) {
        this.MotorItem = motorItem;
    }
}
