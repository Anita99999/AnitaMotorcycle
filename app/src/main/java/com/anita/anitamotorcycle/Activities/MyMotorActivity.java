package com.anita.anitamotorcycle.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anita.anitamotorcycle.R;

public class MyMotorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motor);

        initNavBar(true, "我的摩托车");
    }
}
