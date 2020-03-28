package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities2.Main2Activity;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.anita.anitamotorcycle.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

//延迟三秒后 跳转页面
public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";

    private Timer mtimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Log.d(TAG, "init: ");
//        验证sp中是否存在已登录用户，有则保存用户标记
        final boolean isLogin = UserUtils.isLoginUser(this);
//        验证sp中是否存在摩托车标记，有则保存摩托车标记
        MotorUtils.isExitMotor(this);

        mtimer = new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isLogin) {
//                    用户已登录，跳转到主页
//                    获取登录用户类型
                    int type = UserUtils.getUserType(WelcomeActivity.this);
                    if (type == 1) {
                        toUserMain();
                    } else {
                        toRepairmanMain();
                    }
                } else {
//                    未登录，跳转到登录
                    toLogin();
                }
            }
        }, 3 * 1000);
    }

    /**
     * 跳转到MainActivity
     */
    private void toUserMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();//跳转后关闭页面
    }

    /**
     * 跳转到MainActivity
     */
    private void toRepairmanMain() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();//跳转后关闭页面
    }

    /**
     * 跳转到LoginActivity
     */
    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();//跳转后关闭页面
    }
}
