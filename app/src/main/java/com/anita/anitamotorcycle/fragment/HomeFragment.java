package com.anita.anitamotorcycle.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.MainActivity;
import com.anita.anitamotorcycle.activities.MeActivity;
import com.anita.anitamotorcycle.activities.RepairApplicationActivity;

public class HomeFragment extends Fragment {

    private ImageView mRepairApplication;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initListener();
        return view;
    }


    private void initView(View view) {
        mRepairApplication = view.findViewById(R.id.iv_repair);
    }

    private void initListener() {
        mRepairApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RepairApplicationActivity.class);
                startActivity(intent);
            }
        });
    }
}
