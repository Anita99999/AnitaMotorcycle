package com.anita.anitamotorcycle.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.Activities.LoginActivity;
import com.anita.anitamotorcycle.R;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;


/**
 * @author weizhen
 * @description:用户工具类
 * @date : 2019/9/11 10:37
 */
public class UserUtils {

    /**
     * 验证登录用户手机号输入合法性
     */
    public static boolean validatePhone(Context context, String phone) {
//      简单验证手机号RegexUtils.isMobileSimple(phone);
//      精确验证手机号RegexUtils.isMobileExact(phone);
//        当输入手机号不是精确手机号
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "手机号不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
//        当用户通过验证
        return true;
    }

    /**
     * 验证用户设置的密码
     * 1.两次密码是否输入并相同
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
     * 退出用户登录
     * 跳转到登录页面
     */
    public static void logout(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
//        添加intent标志符：清除当前TASK栈占用的Activity、创建一个新的TASK栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //        定义Activity跳转动画
        ((Activity) context).overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }



}
