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
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.helps.MotorHelper;

public class AddMotorInfoActivity extends AppCompatActivity {
    private static final String TAG = "AddMotorInfoActivity";

    private ImageView mBack;
    private TextView mNext;
    private MotorBean mMotorBean = null;
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

        mMotorBean = MotorHelper.getInstance().getMotorBean();
        Log.d(TAG, "initView: mMotorBean--" + mMotorBean.toString());

        String brand = (mMotorBean.getBrand() == null) ? "" : mMotorBean.getBrand();
        mBrand.setText(brand.toCharArray(), 0, brand.length());
        String model = (mMotorBean.getModel() == null) ? "" : mMotorBean.getModel();
        mModel.setText(model.toCharArray(), 0, model.length());
        String type = (mMotorBean.getType() == null) ? "" : mMotorBean.getType();
        mType.setText(type.toCharArray(), 0, type.length());
        String country = (mMotorBean.getCountry() == null) ? "" : mMotorBean.getCountry();
        mCountry.setText(country.toCharArray(), 0, country.length());
//                        int转string
        mYear.setText((mMotorBean.getYear() + "").toCharArray(), 0, (mMotorBean.getYear() + "").length());
//                        取后6位
        mNumber.setText(mMotorBean.getVin_code().substring(11).toCharArray(), 0, mMotorBean.getVin_code().substring(11).length());
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
                MotorHelper.getInstance().getMotorBean().setBrand(mBrand.getText().toString());
                MotorHelper.getInstance().getMotorBean().setModel(mModel.getText().toString());
                MotorHelper.getInstance().getMotorBean().setType(mType.getText().toString());
                MotorHelper.getInstance().getMotorBean().setCountry(mCountry.getText().toString());
                MotorHelper.getInstance().getMotorBean().setYear(Integer.parseInt(mYear.getText().toString()));
                Log.d(TAG, "确认信息： motor--" + MotorHelper.getInstance().getMotorBean().toString());

                Intent intent = new Intent(getApplicationContext(), AddMotorNumActivity.class);
                startActivity(intent);
            }
        });
    }
}
