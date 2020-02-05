package com.anita.anitamotorcycle.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anita.anitamotorcycle.fragments.HomeFragment;
import com.anita.anitamotorcycle.fragments.MeFragment;
import com.anita.anitamotorcycle.fragments.MessageFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 主界面底部菜单适配器
 * 页面切换，使用Fragment作为子页面
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {

    private static Map<Integer, Fragment> sCache = new HashMap<>();

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

//    返回具体位置的viewPager切换到i位置时对应的fragment
    public Fragment getItem(int i) {
        Fragment fragment = sCache.get(i);
        if (fragment != null) {
            return fragment;
        }
        switch (i) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new MessageFragment();
                break;
            case 2:
                fragment = new MeFragment();
                break;
            default:
                break;
        }
        sCache.put(i, fragment);
        return fragment;
    }

//    返回视图的总数量
    public int getCount() {
        return 3;
    }

}

