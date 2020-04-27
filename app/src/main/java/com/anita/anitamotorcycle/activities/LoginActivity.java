package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities2.Login2Activity;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.anita.anitamotorcycle.views.InputView;
import com.blankj.utilcode.util.EncryptUtils;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private long firstTime = 0;   //第一次点击返回键的时间
    private InputView mInputPhone, mInputPassword;
    private TextView mInlet;
    private Button mLogin;
    private TextView mRegister;

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
                Intent intent = new Intent(LoginActivity.this, Login2Activity.class);
                startActivity(intent);
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
                loginHandler();

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

    private void loginHandler() {
        int isValidate = ClientUtils.validateLogin(LoginActivity.this, mPhone, EncryptUtils.encryptMD5ToString(mPassword));
        if (isValidate < 0) {
            Toast.makeText(this, "该手机号未注册", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isValidate == 0) {
            Toast.makeText(this, "密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }
//              当用户通过验证
//              利用SharedPreferences保存用户登录标记
        boolean isSave = UserUtils.saveUser(LoginActivity.this, mPhone, 1);
        if (isSave) {
            Log.d(TAG, "loginHandler: 保存用户登录标记");
//                  保存用户标记，在全局单例类UserHelp之中
            UserHelper.getInstance().setPhone(mPhone);

            if (MotorHelper.getInstance().getCurrentMotorId() == null && MotorHelper.getInstance().refreshMotorList(LoginActivity.this, mPhone)) {
//                    用户有摩托车数据，且摩托车标记为空
//                        设置摩托车标记,利用SharedPreferences保存摩托车标记
                if (MotorUtils.saveMotor(LoginActivity.this, MotorHelper.getInstance().getMotorList().get(0).getId())) {
//                      保存摩托车标记，在全局单例类MotorHelp之中
                    MotorHelper.getInstance().setCurrentMotorId(MotorHelper.getInstance().getMotorList().get(0).getId());
                } else {
                    Toast.makeText(LoginActivity.this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
//                  跳转到主页面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Looper.prepare();
            Toast.makeText(LoginActivity.this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }


    }

    /**
     * 初始化view
     */
    private void initView() {
        mInlet = findViewById(R.id.tv_repair_man_inlet);

        mInputPhone = findViewById(R.id.input_phone);
        String phone = UserHelper.getInstance().getPhone();
        if (phone != null) {
            mInputPhone.setInputStr(phone);
        }
        mInputPassword = findViewById(R.id.input_password);
        mLogin = findViewById(R.id.btn_login);
        mRegister = findViewById(R.id.tv_register);
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