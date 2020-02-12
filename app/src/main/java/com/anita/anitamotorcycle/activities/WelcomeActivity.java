package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.anita.anitamotorcycle.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

//延迟三秒后 跳转页面
public class WelcomeActivity extends BaseActivity {
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
    private void init(){
//        验证sp中是否存在已登录用户，有则保存用户标记
        final boolean isLogin = UserUtils.isLoginUser(this);
//        验证sp中是否存在摩托车标记，有则保存摩托车标记
        MotorUtils.isExitMotor(this);
        mtimer=new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLogin){
//                    用户已登录，跳转到主页
                    toMain();
                }else{
//                    未登录，跳转到登录
                    toLogin();
                }
            }
        },3*1000);
    }

    /**
     * 跳转到MainActivity
     */
    private  void toMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();//跳转后关闭页面
    }

    /**
     * 跳转到LoginActivity
     */
    private  void toLogin(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();//跳转后关闭页面
    }
}
