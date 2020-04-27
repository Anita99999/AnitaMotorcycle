package com.anita.anitamotorcycle.activities2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.LoginActivity;
import com.anita.anitamotorcycle.activities.MainActivity;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.anita.anitamotorcycle.views.InputView;
import com.blankj.utilcode.util.EncryptUtils;

public class Login2Activity extends AppCompatActivity {

    private ImageView mBack;
    private InputView mInput_phone;
    private InputView mInput_password;
    private Button mBtn_login;
    private TextView mTv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        initView();
        initListener();

    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login2Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 登录按钮
         *  * 1. 验证用户输入的合法性
         *  * 2. 验证手机号是否已注册
         *  * 3. 验证密码是否正确
         */
        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击登录按钮");
                loginValidate();
            }
        });

//        mTv_register
    }

    private void loginValidate() {
        String phone = mInput_phone.getInputStr();
        String password = mInput_password.getInputStr();
//               用户输入数据验证
        if (!UserUtils.validateLogin(this, phone, password)) {
            return;
        }

        Log.d("test", "onClick: ");
//        连接服务端数据库：验证用户手机号是否已注册；验证密码
        int isValidate = ClientUtils.validateRepairmanLogin(phone, EncryptUtils.encryptMD5ToString(password));
        if (isValidate < 0) {
            Toast.makeText(this, "该手机号未注册", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isValidate == 0) {
            Toast.makeText(this, "密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }
//        通过验证
        boolean isSave = UserUtils.saveUser(this, phone, 0);
        if (isSave) {
//              保存用户标记，在全局单例类UserHelp之中
            UserHelper.getInstance().setPhone(phone);
//              跳转到主页面
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mInput_phone = findViewById(R.id.input_phone);
        mInput_password = findViewById(R.id.input_password);
        mBtn_login = findViewById(R.id.btn_login);
        mTv_register = findViewById(R.id.tv_register);
    }
}
