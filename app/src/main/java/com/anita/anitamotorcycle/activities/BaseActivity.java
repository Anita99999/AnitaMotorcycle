package com.anita.anitamotorcycle.activities;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anita.anitamotorcycle.R;

/**
 * Activitu父类
 */
public class BaseActivity extends AppCompatActivity {
    private ImageView myIvBack;
    private TextView myTvTitle;

    /**
     * 初始化navigationbar的显示
     */
    public void initNavBar(boolean isShowBack, String title){
        myIvBack=findViewById(R.id.iv_back);
        myTvTitle=findViewById(R.id.tv_title);

        myIvBack.setVisibility(isShowBack?View.VISIBLE:View.GONE);
        myTvTitle.setText(title);

        myIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });
//
//        myIvMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(BaseActivity.this,MeActivity.class));
//            }
//        });

    }
}