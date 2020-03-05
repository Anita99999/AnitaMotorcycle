package com.anita.anitamotorcycle.helps;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.utils.ClientUtils;

import java.util.List;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/9 10:40
 */
public class MotorHelper {
    private static final String TAG = "MotorHelper";

    //    制订为全局的单例模式
    private static MotorHelper instance;

    private MotorHelper() {
    }

    ;

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

    private String currentMotorId;  //页面显示车辆id
    private MotorBean currentMotor; //页面显示车辆信息

    private List<MotorBean> motorList; //用户所有摩托车

    private MotorBean MotorBean; //添加车辆

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentMotorId() {
        return currentMotorId;
    }

    public void setCurrentMotorId(String currentMotorId) {
        this.currentMotorId = currentMotorId;
    }

    public MotorBean getCurrentMotor() {
        return currentMotor;
    }

    public void setCurrentMotor(MotorBean currentMotor) {
        this.currentMotor = currentMotor;
    }

    public List<MotorBean> getMotorList() {
        return motorList;
    }

    public void setMotorList(List<MotorBean> motorList) {
        this.motorList = motorList;
    }

    public MotorBean getMotorBean() {
        return MotorBean;
    }

    public void setMotorBean(MotorBean motorBean) {
        this.MotorBean = motorBean;
    }

    /**
     * 连接数据库，刷新当前摩托车信息
     *
     * @param context
     */
    public boolean refreshCurrentMotor(Context context) {
        boolean isRefresh;
        Log.d(TAG, "refreshCurrentMotor: ");
        if (currentMotor != null) {
            currentMotor = null;
        }

        currentMotor = ClientUtils.getCurrentMotor(context, currentMotorId);
        if (currentMotor == null) {
            Log.d(TAG, "刷新失败: motorItem==null");
            Toast.makeText(context, "刷新失败！", Toast.LENGTH_SHORT).show();
            isRefresh = false;
        } else {
            Log.d(TAG, "run: 刷新成功");
            isRefresh = true;
        }
        return isRefresh;

    }


    /**
     * 获取所有摩托车数据
     *
     * @param context
     * @param phone
     * @return获取成功且有摩托车数据返回，true
     */
    public boolean refreshMotorList(Context context, String phone) {
        motorList = ClientUtils.getMotorList(context, phone);
        Log.d(TAG, "refreshMotorList: motorList--" + motorList);
        if (motorList != null && !motorList.isEmpty()) {
//            有数据且数据不为空
            Log.d(TAG, "refreshMotorList: not empty");
            return true;
        }else{
            Log.d(TAG, "刷新失败，无摩托车信息: motorItem==null");
//            Toast.makeText(context, "无摩托车信息！", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
