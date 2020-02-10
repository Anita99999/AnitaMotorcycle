package com.anita.anitamotorcycle.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.AddMotorActivity;
import com.anita.anitamotorcycle.activities.BindFirstActivity;
import com.anita.anitamotorcycle.activities.MotorDetailsActivity;
import com.anita.anitamotorcycle.activities.MyMotorActivity;
import com.anita.anitamotorcycle.activities.RepairApplicationActivity;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private RelativeLayout mMotorInfo;
    private LinearLayout mChange_motor;
    private LinearLayout mRepairApplication;
    private LinearLayout mLocation;
    private RelativeLayout mBind;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
//
        return view;
    }


    private void initView(View view) {
        mLocation = view.findViewById(R.id.linlayout_location); //定位
        mChange_motor = view.findViewById(R.id.linlayout_change);   //切换
        mBind = view.findViewById(R.id.relayout_bind_motor);    //绑定
        mMotorInfo = view.findViewById(R.id.relayout_motor_basic_info); //摩托车信息
        mRepairApplication = view.findViewById(R.id.linlayout_repair);  //维修服务

        if (MotorHelper.getInstance().getCurrentMotorId() == null) {
            Log.d(TAG, "initView: ");
//          未添加摩托车
            mMotorInfo.setVisibility(View.INVISIBLE);
            initListener1();
        } else {
            Log.d(TAG, "initView: ");
//            已添加摩托车
            mMotorInfo.setVisibility(View.VISIBLE);
            initListener2();
        }
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

    private void initListener2() {
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
        mMotorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotorDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

}
