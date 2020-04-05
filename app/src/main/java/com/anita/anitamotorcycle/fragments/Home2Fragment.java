package com.anita.anitamotorcycle.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.RepairRecordActivity;
import com.anita.anitamotorcycle.activities2.OrdersRecordActivity;
import com.anita.anitamotorcycle.activities2.TodoOrdersActivity;
import com.anita.anitamotorcycle.adapters.Indicator2Adapter;
import com.anita.anitamotorcycle.adapters.OrdersContentAdapter;
import com.anita.anitamotorcycle.beans.RepairmanBean;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;


public class Home2Fragment extends Fragment {
    private static final String TAG = "Home2Fragment";

    private MagicIndicator mRecord_indicator;
    private ViewPager mVpager_content;
    private Indicator2Adapter mIndicatorAdapter;
    private LinearLayout mLl_todo;
    private LinearLayout mLl_myorders;
    private RepairmanBean mRepairmanBean;

    public Home2Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        Log.d(TAG, "onCreateView: ");
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        TextView tv_today_do = view.findViewById(R.id.tv_today_do);
        TextView tv_today_did = view.findViewById(R.id.tv_today_did);
        TextView tv_total_did = view.findViewById(R.id.tv_total_did);
//        获取维修员信息
        mRepairmanBean = ClientUtils.getRepairmanInfo(UserHelper.getInstance().getPhone());
        if (mRepairmanBean != null) {
            tv_today_do.setText(mRepairmanBean.getToday_do() + "");
            tv_today_did.setText(mRepairmanBean.getToday_did() + "");
            tv_total_did.setText(mRepairmanBean.getTotal_did() + "");
        }

        mLl_todo = view.findViewById(R.id.ll_todo);
        mLl_myorders = view.findViewById(R.id.ll_myorders);
    }

    private void initListener() {
//        代办订单
        mLl_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodoOrdersActivity.class);
                startActivity(intent);
            }
        });
//        我的订单
        mLl_myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersRecordActivity.class);
                startActivity(intent);
            }
        });
    }


}
