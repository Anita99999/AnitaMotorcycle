package com.anita.anitamotorcycle.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.anita.anitamotorcycle.R;

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
        mtimer=new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Log.e("WelcomeActivity","当前线程："+Thread.currentThread());
                //toMain();
                toLogin();
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
