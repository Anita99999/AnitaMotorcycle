package com.anita.anitamotorcycle.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.google.android.material.snackbar.Snackbar;
import com.hb.dialog.dialog.ConfirmDialog;

public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "ScrollingActivity";

    private FloatingActionButton mFab;
    private TextView mTitle;
    private ImageView mBack;
    private Button mDelete;
    private TextView mChoseMotor;
    private MotorBean mMotorBean;
    private TextView mTv_location1;
    private TextView mTv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Log.d(TAG, "onCreate: 摩托车详情页");
        initView();
        initListener();
    }

    private void initListener() {
        mTv_location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollingActivity.this, LocationActivity.class));
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //                利用SharedPreferences保存摩托车标记
                boolean isSave = MotorUtils.saveMotor(ScrollingActivity.this, mMotorBean.getId());
                if (isSave) {
//                      保存摩托车标记，在全局单例类MotorHelp之中
                    MotorHelper.getInstance().setCurrentMotorId(mMotorBean.getId());
                } else {
                    Toast.makeText(ScrollingActivity.this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onClick: 选择摩托车id" + mMotorBean.getId());
                Toast.makeText(getApplicationContext(), "选择车辆" + mMotorBean.getModel() + "成功", Toast.LENGTH_LONG).show();
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 设置返回键和菜单栏可用，可见
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTv_location1 = findViewById(R.id.tv_location1);
        //悬浮按钮
        mFab = findViewById(R.id.fab);

        initContentView();
    }

    @SuppressLint("RestrictedApi")
    private void initContentView() {
        //        获取上一页面传递的数据
        mMotorBean = (MotorBean) getIntent().getSerializableExtra("motor");
        Log.d(TAG, "initView: motor--" + mMotorBean.toString());
//        mBack = findViewById(R.id.iv_back);
//        mChoseMotor = findViewById(R.id.tv_chose_motor);
        mDelete = findViewById(R.id.btn_delete_motor);


        ImageView image = findViewById(R.id.image);
        if (mMotorBean.getUrl() != null) {
            Glide.with(this).load(mMotorBean.getUrl()).placeholder(R.mipmap.network_loading).error(R.mipmap.logo).dontAnimate().into(image);
        }
        TextView tv_vin = findViewById(R.id.tv_vin);
        TextView tv_warranty = findViewById(R.id.tv_warranty);
        mTv_location = findViewById(R.id.tv_location);
        TextView tv_total_distance = findViewById(R.id.tv_total_distance);
        TextView tv_brand = findViewById(R.id.tv_brand);
        TextView tv_model = findViewById(R.id.tv_model);
        TextView tv_country = findViewById(R.id.tv_country);
        TextView tv_type = findViewById(R.id.tv_type);
        TextView tv_year = findViewById(R.id.tv_year);
        TextView tv_nums = findViewById(R.id.tv_nums);
        LinearLayout ll_info1 = findViewById(R.id.ll_info1);
        LinearLayout ll_info2 = findViewById(R.id.ll_info2);
        if (mMotorBean.getStatus() == 0) {
//            扫描获取信息
            //      设置标题
            CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
            collapsingToolbar.setTitle(mMotorBean.getModel());
            collapsingToolbar.setExpandedTitleColor(this.getResources().getColor(R.color.mainTitileColorH));
            ll_info1.setVisibility(View.GONE);
            mTv_location1.setVisibility(View.GONE);
            ll_info2.setVisibility(View.GONE);
            mDelete.setVisibility(View.GONE);
            mFab.setVisibility(View.INVISIBLE);
        } else {
            //      设置标题
            CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
            collapsingToolbar.setTitle(mMotorBean.getPlate_numbers());
            collapsingToolbar.setExpandedTitleColor(this.getResources().getColor(R.color.mainTitileColorH));
            ll_info1.setVisibility(View.VISIBLE);
            mTv_location1.setVisibility(View.VISIBLE);
            ll_info2.setVisibility(View.VISIBLE);
            mDelete.setVisibility(View.VISIBLE);
            mFab.setVisibility(View.VISIBLE);
            tv_vin.setText("车架号：" + mMotorBean.getVin_code());
            tv_warranty.setText("剩余保修期：" + mMotorBean.getWarrantyDays() + "天/" + mMotorBean.getWarrantyDistance() + "公里");
            if (MotorHelper.getInstance().getLocation() != null) {
                mTv_location.setText("当前定位：" + MotorHelper.getInstance().getLocation());
                Log.d(TAG, "initView: mEt_location重新获取定位");
            }
            tv_total_distance.setText("累计里程数：" + mMotorBean.getTotal_distance());
        }
        tv_brand.setText("制造商：" + mMotorBean.getBrand());
        tv_model.setText("车辆型号：" + mMotorBean.getModel());
        tv_country.setText("生产国家：" + mMotorBean.getCountry());
        tv_type.setText("车辆类型：" + mMotorBean.getType());
        tv_year.setText("生产年份：" + mMotorBean.getYear());
        tv_nums.setText("生产顺序号：" + mMotorBean.getVin_code().substring(11));
    }


    private void showConfirmDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认删除该摩托车吗？");
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 摩托车详情页");
        if (MotorHelper.getInstance().getLocation() != null) {
            mTv_location.setText("当前定位：" + MotorHelper.getInstance().getLocation());
            Log.d(TAG, "initView: mEt_location重新获取定位");
        }
    }
}
