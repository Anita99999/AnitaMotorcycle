package com.anita.anitamotorcycle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.anita.anitamotorcycle.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private ImageView mBack;
    private InputView mPassword;
    private InputView mNewPassword;
    private InputView mComfirmPassword;
    private Button mChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
        initListener();

    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = mPassword.getInputStr();
                String newPassword = mNewPassword.getInputStr();
                String passwordConfirm = mComfirmPassword.getInputStr();

                boolean result = UserUtils.changePassword(ChangePasswordActivity.this, UserHelper.getInstance().getPhone(), oldPassword, newPassword, passwordConfirm);
                if (!result) {
                    return;
                }

                UserUtils.logout(ChangePasswordActivity.this);
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mPassword = findViewById(R.id.input_password);
        mNewPassword = findViewById(R.id.input_new_password);
        mComfirmPassword = findViewById(R.id.input_password_confirm);
        mChange = findViewById(R.id.btn_change);

    }

}
