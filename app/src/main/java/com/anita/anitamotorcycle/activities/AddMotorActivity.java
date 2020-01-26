package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anita.anitamotorcycle.R;
import com.hb.dialog.dialog.ConfirmDialog;

public class AddMotorActivity extends BaseActivity {

    private ImageView mBack;
    private TextView mNext;
    private ImageView mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motor);

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

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMotorNumActivity.class);
                startActivity(intent);
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mNext = findViewById(R.id.tv_next);
        mInfo = findViewById(R.id.iv_info);
    }

    private void showAlertDialog() {
        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("车架号")//标题
                .setMessage("仅限添加本品牌（五羊本田）摩托车，下方摩托车信息可自动带入或自行编辑修改。")//内容
                .setIcon(R.mipmap.tips)//图标
                .create();
        alertDialog1.show();

    }
}
