package com.anita.anitamotorcycle.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anita.anitamotorcycle.fragments.RepairedRecordFragment;
import com.anita.anitamotorcycle.fragments.RepairingRecordFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anita
 * @description:维修记录内容适配器
 * @date : 2020/1/5 22:33
 */
public class RecordContentAdapter extends FragmentPagerAdapter {

    private static Map<Integer, Fragment> sCache = new HashMap<>();

    public RecordContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = sCache.get(position);
        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new RepairingRecordFragment();
                break;
            case 1:
                fragment = new RepairedRecordFragment();
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
