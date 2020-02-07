package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;

import com.anita.anitamotorcycle.R;
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
//        验证是否存在已登录用户
        final boolean isLogin = UserUtils.isLoginUser(this);
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
