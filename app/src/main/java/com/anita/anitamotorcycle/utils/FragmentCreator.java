package com.anita.anitamotorcycle.utils;

import androidx.fragment.app.Fragment;

import com.anita.anitamotorcycle.fragments.RepairedRecordFragment;
import com.anita.anitamotorcycle.fragments.RepairingRecordFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anita
 * @description:
 * @date : 2020/1/11 12:38
 */
public class FragmentCreator {

    private static Map<Integer, Fragment> sCache = new HashMap<>();


    public static Fragment getFragment(int index) {
        Fragment fragment = sCache.get(index);
        if (fragment != null) {
            return fragment;
        }

        switch (index) {
            case 0:
                fragment = new RepairingRecordFragment();
                break;
            case 1:
                fragment = new RepairedRecordFragment();
                break;
        }

        sCache.put(index, fragment);
        return fragment;
    }

}
