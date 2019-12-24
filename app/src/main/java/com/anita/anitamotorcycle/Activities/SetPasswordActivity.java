package com.anita.anitamotorcycle.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.Utils.UserUtils;
import com.anita.anitamotorcycle.Views.InputView;

public class SetPasswordActivity extends BaseActivity {

    private Button mBtn_finish;
    private InputView mInput_password;
    private InputView mInput_password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mInput_password = findViewById(R.id.input_password);
        mInput_password_confirm = findViewById(R.id.input_password_confirm);
    }

    /**
     * “完成”按钮点击事件
     * 1.验证两次密码是否输入并相同
     * 2.保存用户密码（MD5加密密码）（未完成）
     *
     * @param view
     */
    public void onFinishClick(View view) {
        String password = mInput_password.getInputStr();
        String passwordConfirm = mInput_password_confirm.getInputStr();
//        验证两次密码是否输入并相同
        boolean result = UserUtils.validatePassword(this, password, passwordConfirm);
        if (!result) return;

        Intent intent = new Intent(this, LoginActivity.class);
//        添加intent标志符：清除当前TASK栈占用的Activity、创建一个新的TASK栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();//跳转后关闭页面
    }

    /**
     * “后退”点击事件
     *
     * @param view
     */
    public void onBackClick(View view) {
        onBackPressed();
    }
}
