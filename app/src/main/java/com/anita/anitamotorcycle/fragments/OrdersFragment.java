package com.anita.anitamotorcycle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.Indicator2Adapter;
import com.anita.anitamotorcycle.adapters.IndicatorAdapter;
import com.anita.anitamotorcycle.adapters.OrdersContentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;


public class OrdersFragment extends Fragment {
    private static final String TAG = "OrdersFragment";

    private MagicIndicator mRecord_indicator;
    private ViewPager mVpager_content;
    private Indicator2Adapter mIndicatorAdapter;

    public OrdersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new Indicator2Adapter.OnIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                Log.d(TAG, "click index is -- >"+ index);
                if(mVpager_content != null) {
                    mVpager_content.setCurrentItem(index,false);
                }
            }
        });
    }

    private void initView(View view) {
        mRecord_indicator = view.findViewById(R.id.record_indicator);
//        mRecord_indicator.setBackgroundColor(this.getResources().getColor(R.color.viewColor));
//        创建indicator的适配器
        mIndicatorAdapter = new Indicator2Adapter(getActivity());
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);
        mRecord_indicator.setNavigator(commonNavigator);

//        创建内容适配器
        mVpager_content = view.findViewById(R.id.vpager_content);
        OrdersContentAdapter ordersContentAdapter = new OrdersContentAdapter(getFragmentManager());
        mVpager_content.setAdapter(ordersContentAdapter);
//        把ViewPager和indicator指示器绑定到一起
        ViewPagerHelper.bind(mRecord_indicator,mVpager_content);
    }

}
