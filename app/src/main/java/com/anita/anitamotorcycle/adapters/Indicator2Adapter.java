package com.anita.anitamotorcycle.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.anita.anitamotorcycle.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * @author Anita
 * @description:订单指示器适配器
 * @date : 2020/1/5 21:57
 */
public class Indicator2Adapter extends CommonNavigatorAdapter {

    private final String[] mTitles;
    private OnIndicatorTapClickListener mOnTabClickListener;

    public Indicator2Adapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.indicator_title2);
    }

    @Override
    public int getCount() {
        if (mTitles != null) {
            return mTitles.length;
        }
        return 0;
    }

    public IPagerTitleView getTitleView(Context context, final int index) {

        //创建view
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        //设置一般情况下的颜色为黑色
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#000000"));
        //设置选中情况下的颜色为主色
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#CC9B39"));
        //单位sp
        colorTransitionPagerTitleView.setTextSize(18);
        //设置要显示的内容
        colorTransitionPagerTitleView.setText(mTitles[index]);
        //设置title的点击事件，选中下面的viewPager到对应的index里面去
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //切换viewPager的内容，如果index不一样的话。
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onTabClick(index);
                }
            }
        });
        //把这个创建好的view返回回去
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setColors(Color.parseColor("#CC9B39"));
        return indicator;
    }

    public void setOnIndicatorTapClickListener(OnIndicatorTapClickListener listener) {
        this.mOnTabClickListener = listener;
    }


    public interface OnIndicatorTapClickListener {
        void onTabClick(int index);
    }
}
