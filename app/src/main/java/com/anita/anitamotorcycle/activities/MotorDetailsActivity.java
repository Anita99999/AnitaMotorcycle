package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.hb.dialog.dialog.ConfirmDialog;

public class MotorDetailsActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mBack;
    private Button mDelete;
    private TextView mChoseMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_details);

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

        mChoseMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "选择成功", Toast.LENGTH_LONG).show();
                onBackPressed();    //后退操作
            }
        });

//        删除按钮
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);

        mTitle = findViewById(R.id.tv_title);
        mTitle.setText(getIntent().getStringExtra("plateNumbers"));

        mChoseMotor = findViewById(R.id.tv_chose_motor);

        mDelete = findViewById(R.id.btn_delete_motor);
    }

    private void showConfirmDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认删除该摩托车吗吗？");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_LONG).show();
                onBackPressed();    //后退操作
            }

            @Override
            public void cancel() {
                Toast.makeText(getApplicationContext(), "取消删除", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
    }
}
