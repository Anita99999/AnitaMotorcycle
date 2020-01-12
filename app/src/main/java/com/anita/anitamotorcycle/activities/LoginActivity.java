package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.views.InputView;

public class LoginActivity extends BaseActivity {
    private long firstTime=0;   //第一次点击返回键的时间
    private InputView mInputPhone,mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView(){

        mInputPhone=findViewById(R.id.input_phone);
        mInputPassword=findViewById(R.id.input_password);

    }

    /**
     * 注册点击事件
     * 跳转到注册页面
     */
    public void onRegisterUIClick(View v){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录按钮点击事件
     * 验证用户输入的合法性
     */
    public void onCommitClick(View v){
//        String phone=mInputPhone.getInputStr();
//        String password=mInputPassword.getInputStr();
//
////        用户输入验证不通过
//        if(!UserUtils.validateLogin(this,phone ,password)){
//            return;
//        }
//      验证通过，跳转到MainActivity 应用主页
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //  点击两次返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-firstTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}