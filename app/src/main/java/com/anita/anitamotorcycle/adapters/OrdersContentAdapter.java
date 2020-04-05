package com.anita.anitamotorcycle.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anita.anitamotorcycle.fragments.FinishedFragment;
import com.anita.anitamotorcycle.fragments.Me2Fragment;
import com.anita.anitamotorcycle.fragments.MessageFragment;
import com.anita.anitamotorcycle.fragments.RepairedRecordFragment;
import com.anita.anitamotorcycle.fragments.RepairingRecordFragment;
import com.anita.anitamotorcycle.fragments.ToHandFragment;
import com.anita.anitamotorcycle.fragments.ToRepairFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anita
 * @description:订单内容适配器
 * @date : 2020/1/5 22:33
 */
public class OrdersContentAdapter extends FragmentPagerAdapter {

    private static Map<Integer, Fragment> sCache = new HashMap<>();

    public OrdersContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = sCache.get(position);
        if (fragment != null) {
            return fragment;
        }
//        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ToRepairFragment();
                break;
            case 1:
                fragment = new FinishedFragment();
                break;
        }
        sCache.put(position, fragment);
        return fragment;
    }

    //    返回视图的总数量
    @Override
    public int getCount() {
        return 2;
    }
}
