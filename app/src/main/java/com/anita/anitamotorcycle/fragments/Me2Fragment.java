package com.anita.anitamotorcycle.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.MeActivity;
import com.anita.anitamotorcycle.activities.MyMotorActivity;
import com.anita.anitamotorcycle.activities.RepairRecordActivity;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.beans.RepairmanBean;
import com.anita.anitamotorcycle.beans.UserBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.Constants;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.leon.lib.settingview.LSettingItem;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Me2Fragment extends Fragment {
    private static final String TAG = "Me2Fragment";
    private CircleImageView iv_user;
    private TextView tv_username;
    private Button btn_logout;

    private RepairmanBean mRepairmanBean;

    public Me2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me2, container, false);
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
        TextView tv_factory_name = view.findViewById(R.id.tv_factory_name);
        TextView tv_today_do = view.findViewById(R.id.tv_today_do);
        TextView tv_today_did = view.findViewById(R.id.tv_today_did);
        TextView tv_total_did = view.findViewById(R.id.tv_total_did);

//       退出登录按钮
        btn_logout = view.findViewById(R.id.btn_logout);

//        获取维修员信息
        mRepairmanBean = ClientUtils.getRepairmanInfo(UserHelper.getInstance().getPhone());
        if (mRepairmanBean != null) {
            tv_username.setText(mRepairmanBean.getName());
            tv_factory_name.setText(mRepairmanBean.getFactory_id());
            tv_today_do.setText(mRepairmanBean.getToday_do()+"");
            tv_today_did.setText(mRepairmanBean.getToday_did()+"");
            tv_total_did.setText(mRepairmanBean.getTotal_did()+"");
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
                /*Intent intent = new Intent(getActivity(), MeActivity.class);
                intent.putExtra("user",mUserBean);
                startActivityForResult(intent, 1);   //返回数据*/
            }
        });
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), MeActivity.class);
                intent.putExtra("user",mUserBean);
                startActivityForResult(intent, 1);   //返回数据*/
            }
        });

//        退出登录
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtils.logout(getActivity(), 2);
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
