package com.anita.anitamotorcycle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anita.anitamotorcycle.R;

/**
 * @author Anita
 * @description:维修记录中的已完成
 * * @date : 2020/1/5 22:49
 */
public class RepairCompletedFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repair_completed, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
