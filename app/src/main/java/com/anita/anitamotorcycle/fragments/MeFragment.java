package com.anita.anitamotorcycle.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anita.anitamotorcycle.activities.MeActivity;

import com.anita.anitamotorcycle.activities.MyMotorActivity;
import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.RepairRecordActivity;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.beans.UserBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.Constants;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.leon.lib.settingview.LSettingItem;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends Fragment {
    private static final String TAG = "MeFragment";
    private CircleImageView iv_user;
    private TextView tv_username;
    private Button btn_logout;

    private LSettingItem mlsi_repair_record;
    private LSettingItem mlsi_my_motor;
    private UserBean mUserBean;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_me, container, false);
//        初始化view
        initView(view);
//        设置条目点击事件
        initListener();
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
        TextView tv_today_runtime = view.findViewById(R.id.tv_today_runtime);
        TextView tv_today_distance = view.findViewById(R.id.tv_today_distance);
        TextView tv_total_distance = view.findViewById(R.id.tv_total_distance);

        mlsi_my_motor = view.findViewById(R.id.lsi_my_motor);
        mlsi_repair_record = view.findViewById(R.id.lsi_repair_record);
//       退出登录按钮
        btn_logout = view.findViewById(R.id.btn_logout);

//        获取用户信息
        mUserBean = ClientUtils.getUserInfo(UserHelper.getInstance().getPhone());
        if (mUserBean != null) {
            tv_username.setText(mUserBean.getName());
        }

//          有摩托车标记
        if (MotorHelper.getInstance().getCurrentMotorId() != null) {
//          刷新当前摩托车信息
            boolean isRefresh = MotorHelper.getInstance().refreshCurrentMotor(getContext());
            if (isRefresh) {
                MotorBean motorbean = MotorHelper.getInstance().getCurrentMotor();
                tv_today_runtime.setText(motorbean.getToday_runtime() + "分钟");
                tv_today_distance.setText(motorbean.getToday_distance() + "公里");
                tv_total_distance.setText(motorbean.getTotal_distance() + "公里");
                mlsi_my_motor.setRightText(motorbean.getModel());
            }

        }
    }

    /**
     * 控件点击事件
     */
    private void initListener() {
//        用户名及用户头像
        tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeActivity.class);
                intent.putExtra("user", mUserBean);
                startActivityForResult(intent, 1);   //返回数据
            }
        });
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MeActivity.class);
                intent.putExtra("user", mUserBean);
                startActivityForResult(intent, 1);   //返回数据
            }
        });

//        “我的摩托车”点击事件
        mlsi_my_motor.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(getActivity(), MyMotorActivity.class));
            }
        });

//        “维修记录”点击事件
        mlsi_repair_record.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(getActivity(), RepairRecordActivity.class));
            }
        });

//        退出登录
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtils.logout(getActivity(), 1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode==" + requestCode + " resultCode==" + resultCode);
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
