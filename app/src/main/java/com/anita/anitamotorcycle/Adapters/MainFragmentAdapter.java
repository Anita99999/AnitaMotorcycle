package com.anita.anitamotorcycle.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anita.anitamotorcycle.Fragment.HomeFragment;
import com.anita.anitamotorcycle.Fragment.MeFragment;
import com.anita.anitamotorcycle.Fragment.MessageFragment;
import com.anita.anitamotorcycle.Fragment.ShopFragment;

/**
 * 主界面底部菜单适配器
 * 页面切换，使用Fragment作为子页面
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

//    返回具体位置的viewPager切换到i位置时对应的fragment
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ShopFragment();
                break;
            case 2:
                fragment = new MessageFragment();
                break;
            case 3:
                fragment = new MeFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

//    返回视图的总数量
    public int getCount() {
        return 4;
    }

}

