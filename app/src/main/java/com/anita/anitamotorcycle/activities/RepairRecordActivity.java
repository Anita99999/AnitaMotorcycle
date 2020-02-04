package com.anita.anitamotorcycle.activities;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.IndicatorAdapter;
import com.anita.anitamotorcycle.adapters.RecordContentAdapter;
import com.google.android.material.tabs.TabLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class RepairRecordActivity extends BaseActivity {

    private MagicIndicator mRecord_indicator;
    private ViewPager mVpager_content;
    private IndicatorAdapter mIndicatorAdapter;
    private static final String TAG = "RepairRecordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record);

        initView();
        initListener();
    }

    private void initListener() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
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
        initNavBar(true, "维修记录");

        mRecord_indicator = this.findViewById(R.id.record_indicator);
//        mRecord_indicator.setBackgroundColor(this.getResources().getColor(R.color.viewColor));
//        创建indicator的适配器
        mIndicatorAdapter = new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);
        mRecord_indicator.setNavigator(commonNavigator);

//        创建内容适配器
        mVpager_content = this.findViewById(R.id.vpager_content);
        RecordContentAdapter recordContentAdapter = new RecordContentAdapter(getSupportFragmentManager());
        mVpager_content.setAdapter(recordContentAdapter);
//        把ViewPager和indicator指示器绑定到一起
        ViewPagerHelper.bind(mRecord_indicator,mVpager_content);
    }
}
