package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.anita.anitamotorcycle.views.InputView;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private long firstTime = 0;   //第一次点击返回键的时间
    private InputView mInputPhone, mInputPassword;
    private TextView mInlet;
    private Button mLogin;
    private TextView mRegister;

    private int sResult;
    private String mPhone;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }


    private void initListener() {
//        维修员入口
        mInlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        /**
         * 登录按钮
         * 1. 验证用户输入的合法性
         * 2. 验证手机号是否已注册
         * 3. 验证密码是否正确
         */
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击登录按钮");
                mPhone = mInputPhone.getInputStr();
                mPassword = mInputPassword.getInputStr();

//               用户输入数据验证
                if (!UserUtils.validateLogin(LoginActivity.this, mPhone, mPassword)) {
                    return;
                }

                Log.d("test", "onClick: ");
//                连接服务端数据库：验证用户手机号已注册；验证密码正确
                Thread accessWebServiceThread = new Thread(new WebServiceHandler());
                accessWebServiceThread.start();



            }
        });

        /**
         * 注册点击事件
         * 跳转到注册页面
         */
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化view
     */
    private void initView() {
        mInlet = findViewById(R.id.tv_repair_man_inlet);

        mInputPhone = findViewById(R.id.input_phone);
        mInputPassword = findViewById(R.id.input_password);
        mLogin = findViewById(R.id.btn_login);
        mRegister = findViewById(R.id.tv_register);
    }

    /**
     * 1. 连接服务端数据库
     * 2. 验证用户手机号已注册
     * 3. 验证密码正确
     */
    class WebServiceHandler implements Runnable {
        @Override
        public void run() {
            if (ClientUtils.validateLoginPost(LoginActivity.this, mPhone, mPassword)) {
//              当用户通过验证
//              利用SharedPreferences保存用户登录标记
                boolean isSave = UserUtils.saveUser(LoginActivity.this, mPhone);
                if (isSave) {
//                  保存用户标记，在全局单例类UserHelp之中
                    UserHelper.getInstance().setPhone(mPhone);

//                  跳转到主页面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();

                }

            }
        }
    }

    //  点击两次返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - firstTime) > 2000) {
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