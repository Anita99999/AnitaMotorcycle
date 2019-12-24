package com.anita.anitamotorcycle.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anita.anitamotorcycle.Activities.MeActivity;

import com.anita.anitamotorcycle.Activities.MyMotorActivity;
import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.Utils.Constants;
import com.anita.anitamotorcycle.Utils.UserUtils;
import com.leon.lib.settingview.LSettingItem;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MeFragment";
    private CircleImageView iv_user;
    private TextView tv_username;
    private Button btn_logout;
    private LSettingItem my_motor;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
//        初始化view
        initView(view);
//        设置条目点击事件
        initOnLSettingItemClick();
        return view;
    }

    /**
     * 初始化view
     *
     * @param view
     */
    private void initView(View view) {
//        初始化头像图片
        iv_user = view.findViewById(R.id.iv_user);
        iv_user.setOnClickListener(this);
        File sdpath = new File(Constants.SD_PATH);
        if (sdpath.exists()) {
            Bitmap bt = BitmapFactory.decodeFile(Constants.SD_PATH + "head.jpg");//从Sd中找头像，转换成Bitmap
            if (bt != null) {
                //如果本地有头像图片的话
                iv_user.setImageBitmap(bt);
                Log.d(TAG, "initView: 本地有头像图片");
            } else {
                //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略
                Log.d(TAG, "initView: 本地无头像图片");
            }
        } else {
            Log.d(TAG, "initView: sdpath不存在");
        }
//        用户名
        tv_username = view.findViewById(R.id.tv_username);
        tv_username.setOnClickListener(this);

        my_motor = view.findViewById(R.id.lsi_my_motor);

//       退出登录按钮
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
    }

    /**
     * LSettingItem点击事件
     */
    private void initOnLSettingItemClick() {
//        “我的摩托车”点击事件
        my_motor.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(getActivity(), MyMotorActivity.class));
            }
        });
    }

    /**
     * 普通控件点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == iv_user || v == tv_username) {
            Intent intent = new Intent(getActivity(), MeActivity.class);
            startActivityForResult(intent, 1);   //返回数据
//        退出登录按钮点击事件
        } else if (v == btn_logout) {
            UserUtils.logout(getActivity());
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode==" + requestCode + " resultCode==" + resultCode);
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//          打开MeActivity后返回
            case 1:
//                下一MeActivity中的setResult()方法中resultCode=2的数据
                if (resultCode == 2) {
//                    下一个Activity中putExtra()方法中的数据“data”
                    byte[] bis = data.getByteArrayExtra("data");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                    iv_user.setImageBitmap(bitmap);
                }
                break;
        }
    }

}
