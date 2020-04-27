package com.anita.anitamotorcycle.activities2;

import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.BaseActivity;
import com.anita.anitamotorcycle.adapters.Indicator2Adapter;
import com.anita.anitamotorcycle.adapters.IndicatorAdapter;
import com.anita.anitamotorcycle.adapters.OrdersContentAdapter;
import com.anita.anitamotorcycle.adapters.RecordContentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class OrdersRecordActivity extends BaseActivity {

    private MagicIndicator mRecord_indicator;
    private ViewPager mVpager_content;
    private Indicator2Adapter mIndicatorAdapter;
    private static final String TAG = "OrdersRecordActivity";
    private OrdersContentAdapter mOrdersContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_record);

        initView();
        initListener();
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

    private void initView() {
        initNavBar(true, "我的订单");

        mRecord_indicator = this.findViewById(R.id.record_indicator);
//        mRecord_indicator.setBackgroundColor(this.getResources().getColor(R.color.viewColor));
//        创建indicator的适配器
        mIndicatorAdapter = new Indicator2Adapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);
        mRecord_indicator.setNavigator(commonNavigator);

//        创建内容适配器
        mVpager_content = this.findViewById(R.id.vpager_content);
        mOrdersContentAdapter = new OrdersContentAdapter(getSupportFragmentManager());
        mVpager_content.setAdapter(mOrdersContentAdapter);
//        把ViewPager和indicator指示器绑定到一起
        ViewPagerHelper.bind(mRecord_indicator,mVpager_content);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }
}
