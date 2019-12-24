package com.anita.anitamotorcycle.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anita.anitamotorcycle.Activities.BaseActivity;
import com.anita.anitamotorcycle.Activities.MeActivity;
import com.anita.anitamotorcycle.R;


public class MessageFragment extends Fragment {


    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }



}
