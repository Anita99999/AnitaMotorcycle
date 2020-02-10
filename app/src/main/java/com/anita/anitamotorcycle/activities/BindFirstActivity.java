package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anita.anitamotorcycle.R;

public class BindFirstActivity extends AppCompatActivity {

    private ImageView mBack;
    private TextView mAddMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_first);

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

        mAddMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMotorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mAddMotor = findViewById(R.id.tv_add_motor);
    }
}
