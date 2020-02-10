package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.helps.MotorHelper;

public class AddMotorInfoActivity extends AppCompatActivity {
    private static final String TAG = "AddMotorInfoActivity";

    private ImageView mBack;
    private TextView mNext;
    private MotorItem mMotorItem = null;
    private EditText mBrand;
    private EditText mModel;
    private EditText mType;
    private EditText mCountry;
    private EditText mYear;
    private EditText mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motor_info);

        initView();
        initListener();
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mNext = findViewById(R.id.tv_next);

        mBrand = findViewById(R.id.et_brand);
        mModel = findViewById(R.id.et_model);
        mType = findViewById(R.id.et_type);
        mCountry = findViewById(R.id.et_country);
        mYear = findViewById(R.id.et_year);
        mNumber = findViewById(R.id.et_number);

        mMotorItem = MotorHelper.getInstance().getMotorItem();
        Log.d(TAG, "initView: mMotorItem--" + mMotorItem.toString());
        mBrand.setText(mMotorItem.getBrand().toCharArray(), 0, mMotorItem.getBrand().length());
        mModel.setText(mMotorItem.getModel().toCharArray(), 0, mMotorItem.getModel().length());
        mType.setText(mMotorItem.getType().toCharArray(), 0, mMotorItem.getType().length());
        mCountry.setText(mMotorItem.getCountry().toCharArray(), 0, mMotorItem.getCountry().length());
//                        int转string
        mYear.setText((mMotorItem.getYear() + "").toCharArray(), 0, (mMotorItem.getYear() + "").length());
//                        取后6位
        mNumber.setText(mMotorItem.getVin_code().substring(11).toCharArray(), 0, mMotorItem.getVin_code().substring(11).length());
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
//                保存修改信息
                MotorHelper.getInstance().getMotorItem().setBrand(mBrand.getText().toString());
                MotorHelper.getInstance().getMotorItem().setModel(mModel.getText().toString());
                MotorHelper.getInstance().getMotorItem().setType(mType.getText().toString());
                MotorHelper.getInstance().getMotorItem().setCountry(mCountry.getText().toString());
                MotorHelper.getInstance().getMotorItem().setYear(Integer.parseInt(mYear.getText().toString()));
                MotorHelper.getInstance().getMotorItem().setNumber(Integer.parseInt(mNumber.getText().toString()));
                Log.d(TAG, "确认信息： motor--" + MotorHelper.getInstance().getMotorItem().toString());

                Intent intent = new Intent(getApplicationContext(), AddMotorNumActivity.class);
                startActivity(intent);
            }
        });
    }
}
