package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.hb.dialog.myDialog.MyAlertDialog;

public class AddMotorNumActivity extends AppCompatActivity {
    private ImageView mBack;
    private TextView mOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motor_num);

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

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();  //弹窗确认车牌号
            }


        });
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mOk = findViewById(R.id.tv_ok);

    }

    /**
     * 弹窗确认车牌号
     */
    private void showAlertDialog() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                .setTitle("确认吗？")
                .setMsg("添加车辆粤YYP573")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddMotorNumActivity.this, MyMotorActivity.class);
//        添加intent标志符：把要启动的Activity之上的Activity全部弹出栈空间
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_LONG).show();
                    }
                });
        myAlertDialog.show();
    }
}
