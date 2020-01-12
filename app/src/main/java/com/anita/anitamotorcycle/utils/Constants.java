package com.anita.anitamotorcycle.utils;

import android.os.Environment;

/**
 * @author weizhen
 * @description:
 * @date : 2019/10/14 22:57
 */
public class Constants {
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MotorData/";//sd路径
    public static final int MESSAGE_COUNTDOWNTIME = 60; //注册时短信验证码发送倒计时

}
