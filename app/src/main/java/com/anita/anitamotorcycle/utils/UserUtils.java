package com.anita.anitamotorcycle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.activities.LoginActivity;
import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities2.Login2Activity;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author weizhen
 * @description:用户工具类
 * @date : 2019/9/11 10:37
 */
public class UserUtils {
    private static final String TAG = "UserUtils";

    /**
     * 验证登录用户
     * 1. 验证用户手机号及密码
     */
    public static boolean validateLogin(Context context, String phone, String password) {
//        验证手机号
        if (!validatePhone(context, phone)) {
            return false;
        }
//        验证密码
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "密码不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 验证用户手机号
     *
     * @param context
     * @param phone
     * @return
     */
    public static boolean validatePhone(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "手机号不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }

//        当输入手机号不是精确手机号
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

//        当用户通过验证
        return true;
    }

    /**
     * 验证用户设置的密码
     * 1.两次密码是否不为空并相同
     *
     * @param context
     * @return
     */
    public static boolean validatePassword(Context context, String password, String passwordConfirm) {
//        验证两次密码是否输入并相同
        if (StringUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 修改密码
     * 1、数据验证
     * 1、原密码是否输入
     * 2、新密码是否输入,且新密码与确定密码是否相同
     * 3、原密码输入是否正确(查数据库，TODO)
     */
    public static boolean changePassword(Context context, String phone, String oldPassword, String password, String passwordConfirm) {
//        原密码是否输入
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
//        新密码是否输入,且新密码与确定密码是否相同
        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认新密码", Toast.LENGTH_SHORT).show();
            return false;
        }

//        连接数据库，验证原密码是否正确，正确修改成功，失败
        boolean isRight = ClientUtils.validatePassword(phone, EncryptUtils.encryptMD5ToString(oldPassword), EncryptUtils.encryptMD5ToString(password));
        if (!isRight) {
            Toast.makeText(context, "原密码输入错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(context, "修改密码成功，请重新登录", Toast.LENGTH_SHORT).show();
        return true;
    }


    /**
     * 退出用户登录
     * 1. 删除sp保存的用户标记
     * 2. 跳转到登录页面
     * type:1 退出到用户登录页面；2退出到维修员的登录界面
     */
    public static void logout(Context context, int type) {
//        删除sp保存的用户标记
        boolean isRemove = removeUser(context);
        if (!isRemove) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent;
        if(type==1){
            intent = new Intent(context, LoginActivity.class);
        }else{
            intent = new Intent(context, Login2Activity.class);
        }
//        添加intent标志符：清除当前TASK栈占用的Activity、创建一个新的TASK栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        定义Activity跳转动画
        ((Activity) context).overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }

    /**
     * 利用SharedPreferences，保存登录用户的用户标记（手机号码）
     *type=1：用户；type=0：维修员
     * @param context
     * @param phone
     * @return 是否保存成功
     */
    public static boolean saveUser(Context context, String phone, int type) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//        将phone的值传入常量中
        editor.putString("phone", phone);
        editor.putInt("type", type);
        boolean result = editor.commit();
        return result;
    }

    /**
     * 验证是否存在已登录用户
     * 即SharedPreferences中是否有保存phone
     */
    public static boolean isLoginUser(Context context) {
        boolean result = false;
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
//        取出常量中的内容
        String phone = sp.getString("phone", "");
//        取出的内容不为空
        if (!TextUtils.isEmpty(phone)) {
            result = true;
//            保存用户标记，在全局单例类UserHelp之中
            UserHelper.getInstance().setPhone(phone);
        }
        return result;
    }

    /**
     * 获取登录用户类型
     * 即SharedPreferences中的type值
     */
    public static int getUserType(Context context) {
        int type = 1;
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
//        取出常量中的内容
        type = sp.getInt("type", 1);
        return type;
    }

    /**
     * 删除用户标记
     */
    public static boolean removeUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("phone");
        editor.remove("type");
        boolean result = editor.commit();
        UserHelper.getInstance().setPhone(null);

        return result;
    }

    /**
     * 获取当前北京时间
     * @return
     */
    public static String getCurrentTime(){
        // 获取当前北京时间,设置创建时间和更新时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String currentDate = sdf.format(date);// new Date()为获取当前系统时间
        return currentDate;
    }

}
