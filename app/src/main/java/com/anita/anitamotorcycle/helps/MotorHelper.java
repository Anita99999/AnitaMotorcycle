package com.anita.anitamotorcycle.helps;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.utils.ClientUtils;

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
    private MotorBean MotorBean; //添加车辆
    private Context context; //添加车辆

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

    public MotorBean getMotorBean() {
        return MotorBean;
    }

    public void setMotorBean(MotorBean motorBean) {
        this.MotorBean = motorBean;
    }

    /**
     * 连接数据库，刷新当前摩托车信息
     *
     * @param con
     */
    public void refreshCurrentMotor(Context con) {
        Log.d(TAG, "refreshCurrentMotor: ");
        if (currentMotor != null) {
            currentMotor = null;
        }
        context = con;
        //创建子线程，并启动子线程；访问服务器，更新数摩托车数据
        Thread getMotorThread = new Thread(new GetMotorThread());
        getMotorThread.start();
        try {
//            主线程等待子线程结束
            getMotorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getMotorThread done!");


    }

    class GetMotorThread implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "子线程:GetMotorThread ");
            currentMotor = ClientUtils.getCurrentMotor(context, currentMotorId);
            if (currentMotor == null) {
                Log.d(TAG, "刷新失败: motorItem==null");
                Looper.prepare();
                Toast.makeText(context, "服务器出错！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                Log.d(TAG, "run: 刷新成功");
                Log.d(TAG, "run: currentMotor--" + currentMotor.toString());
            }
        }
    }


}
