package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.hb.dialog.dialog.ConfirmDialog;

public class MotorDetailsActivity extends AppCompatActivity {
    private static final String TAG = "MotorDetailsActivity";

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
//        获取上一页面传递的数据
        MotorBean motor = (MotorBean) getIntent().getSerializableExtra("motor");
        Log.d(TAG, "initView: motor--" + motor.toString());
        mBack = findViewById(R.id.iv_back);
        mChoseMotor = findViewById(R.id.tv_chose_motor);
        mDelete = findViewById(R.id.btn_delete_motor);

        mTitle = findViewById(R.id.tv_title);
        mTitle.setText(motor.getPlate_numbers());
        TextView tv_vin = findViewById(R.id.tv_vin);
        tv_vin.setText("车架号：" + motor.getVin_code());
        TextView tv_warranty = findViewById(R.id.tv_warranty);
        tv_warranty.setText("剩余保修期：" + motor.getWarrantyDays() + "天/" + motor.getWarrantyDistance() + "公里");
        TextView tv_location = findViewById(R.id.tv_location);
        tv_location.setText("当前定位：");
        TextView tv_total_distance = findViewById(R.id.tv_total_distance);
        tv_total_distance.setText("累计里程数：" + motor.getTotal_distance());
        TextView tv_brand = findViewById(R.id.tv_brand);
        tv_brand.setText("制造商：" + motor.getBrand());
        TextView tv_model = findViewById(R.id.tv_model);
        tv_model.setText("车辆型号：" + motor.getModel());
        TextView tv_country = findViewById(R.id.tv_country);
        tv_country.setText("生产国家：" + motor.getCountry());
        TextView tv_type = findViewById(R.id.tv_type);
        tv_type.setText("车辆类型：" + motor.getType());
        TextView tv_year = findViewById(R.id.tv_year);
        tv_year.setText("生产年份：" + motor.getYear());
        TextView tv_nums = findViewById(R.id.tv_nums);
        tv_nums.setText("生产顺序号：" + motor.getVin_code().substring(11));

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
