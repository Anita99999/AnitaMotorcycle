package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anita.anitamotorcycle.R;

public class AddMotorActivity extends BaseActivity {

    private ImageView mBack;
    private TextView mNext;

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
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mNext = findViewById(R.id.tv_next);
    }
}
