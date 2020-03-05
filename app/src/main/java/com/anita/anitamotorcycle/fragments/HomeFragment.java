package com.anita.anitamotorcycle.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.AddMotorActivity;
import com.anita.anitamotorcycle.activities.BaiduActivity;
import com.anita.anitamotorcycle.activities.BindFirstActivity;
import com.anita.anitamotorcycle.activities.LocationActivity;
import com.anita.anitamotorcycle.activities.MotorDetailsActivity;
import com.anita.anitamotorcycle.activities.MyMotorActivity;
import com.anita.anitamotorcycle.activities.RepairApplicationActivity;
import com.anita.anitamotorcycle.activities.ScrollingActivity;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.bumptech.glide.Glide;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private RelativeLayout mRelayout_motor_basic_info;
    private LinearLayout mChange_motor;
    private LinearLayout mRepairApplication;
    private LinearLayout mLocation;
    private RelativeLayout mBind;
    private ImageView mIv_motor;
    private TextView mTv_warranty_period;
    private TextView mTv_model;
    private TextView mTv_year;
    private TextView mTv_type;
    private MotorBean mMotorBean = new MotorBean();
    private View mView;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(mView);
//
        return mView;
    }


    private void initView(View view) {
        Log.d(TAG, "initView: ");

        mLocation = view.findViewById(R.id.linlayout_location); //定位
        mChange_motor = view.findViewById(R.id.linlayout_change);   //切换

        mBind = view.findViewById(R.id.relayout_bind_motor);    //绑定
        mRelayout_motor_basic_info = view.findViewById(R.id.relayout_motor_info); //摩托车信息
        mIv_motor = view.findViewById(R.id.iv_motor);
        mTv_warranty_period = view.findViewById(R.id.tv_warranty_period);
        mTv_model = view.findViewById(R.id.tv_model);
        mTv_year = view.findViewById(R.id.tv_year);
        mTv_type = view.findViewById(R.id.tv_type);

        mRepairApplication = view.findViewById(R.id.linlayout_repair);  //维修服务
        Log.d(TAG, "initView: 摩托车标记--" + MotorHelper.getInstance().getCurrentMotorId());
        if (MotorHelper.getInstance().getCurrentMotorId() == null) {
//          无摩托车标记
            mRelayout_motor_basic_info.setVisibility(View.INVISIBLE);
            initListener1();
        } else {
//          有摩托车标记
            mRelayout_motor_basic_info.setVisibility(View.VISIBLE);
            showMotorView();
            initListener2();
        }
    }

    private void showMotorView() {
//        刷新当前摩托车信息
        boolean isRefresh = MotorHelper.getInstance().refreshCurrentMotor(getContext());
        if (!isRefresh) {
            return;
        }

        mMotorBean = MotorHelper.getInstance().getCurrentMotor();
//        设置页面摩托车信息
        if (mMotorBean.getUrl() != null) {
            Glide.with(getActivity()).load(mMotorBean.getUrl()).placeholder(R.mipmap.network_loading).error(R.mipmap.logo).dontAnimate().into(mIv_motor);
        }
        mTv_warranty_period.setText("保修期剩余：" + mMotorBean.getWarrantyDays() + "天/" + mMotorBean.getWarrantyDistance() + "公里");
        mTv_model.setText(mMotorBean.getModel());
        mTv_year.setText(mMotorBean.getYear() + "");
        mTv_type.setText(mMotorBean.getType());
    }

    /**
     * 未添加摩托车时的点击事件
     */
    private void initListener1() {
//        定位
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BindFirstActivity.class));
            }
        });
//        切换
        mChange_motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BindFirstActivity.class));
            }
        });

        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddMotorActivity.class));
            }
        });
//        维修服务
        mRepairApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BindFirstActivity.class);
                startActivity(intent);
            }
        });
    }

    //    有摩托车标记时的点击事件
    private void initListener2() {
//        定位
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });
//        切换
        mChange_motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyMotorActivity.class));
            }
        });
//        维修服务
        mRepairApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RepairApplicationActivity.class);
                startActivity(intent);
            }
        });

//        摩托车信息
        mRelayout_motor_basic_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScrollingActivity.class);
                intent.putExtra("motor", mMotorBean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        initView(mView);
        Log.d(TAG, "onResume: 数据回显");
    }
}
