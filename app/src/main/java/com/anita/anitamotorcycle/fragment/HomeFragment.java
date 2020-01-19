package com.anita.anitamotorcycle.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.MainActivity;
import com.anita.anitamotorcycle.activities.MeActivity;
import com.anita.anitamotorcycle.activities.MyMotorActivity;
import com.anita.anitamotorcycle.activities.RepairApplicationActivity;

public class HomeFragment extends Fragment {

    private RelativeLayout mMotorInfo;
    private LinearLayout mChange_motor;
    private LinearLayout mRepairApplication;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initListener();
        return view;
    }


    private void initView(View view) {
        mChange_motor = view.findViewById(R.id.linlayout_change);   //切换
        mMotorInfo = view.findViewById(R.id.relayout_motor_basic_info); //摩托车信息
        mRepairApplication = view.findViewById(R.id.linlayout_repair);  //维修服务
    }

    private void initListener() {
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
    }
}
