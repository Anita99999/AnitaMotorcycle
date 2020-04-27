package com.anita.anitamotorcycle.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.AddMotorActivity;
import com.anita.anitamotorcycle.activities.BindFirstActivity;
import com.anita.anitamotorcycle.activities.LocationActivity;
import com.anita.anitamotorcycle.activities.MyMotorActivity;
import com.anita.anitamotorcycle.activities.QRCodeActivity;
import com.anita.anitamotorcycle.activities.RepairApplicationActivity;
import com.anita.anitamotorcycle.activities.ScrollingActivity;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.PermissionUtils;
import com.bumptech.glide.Glide;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.security.Permission;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private RelativeLayout mRelayout_motor_basic_info;
    private LinearLayout mChange_motor;
    private LinearLayout mRepairApplication;
    private LinearLayout mLocation;
    private RelativeLayout mBind;
    private ImageView mIv_motor;
    private TextView mTv_warranty_period;
    private TextView mTv_model;
    private TextView mTv_year;
    private TextView mTv_type;
    private MotorBean mMotorBean = new MotorBean();
    private String currentMotorId;
    private View mView;
    private ImageView mIv_erweima;
    private ImageView mIv_scan;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(mView);
        return mView;
    }


    private void initView(View view) {
        Log.d(TAG, "initView: ");

        mIv_scan = view.findViewById(R.id.iv_scan);
        mLocation = view.findViewById(R.id.linlayout_location); //定位
        mChange_motor = view.findViewById(R.id.linlayout_change);   //切换

        mBind = view.findViewById(R.id.relayout_bind_motor);    //绑定
        mRelayout_motor_basic_info = view.findViewById(R.id.relayout_motor_info); //摩托车信息
        mIv_motor = view.findViewById(R.id.iv_motor);
        mTv_warranty_period = view.findViewById(R.id.tv_warranty_period);
        mTv_model = view.findViewById(R.id.tv_model);
        mTv_year = view.findViewById(R.id.tv_year);
        mTv_type = view.findViewById(R.id.tv_type);

        mRepairApplication = view.findViewById(R.id.linlayout_repair);  //维修服务
        Log.d(TAG, "initView: 摩托车标记--" + MotorHelper.getInstance().getCurrentMotorId());
        if (MotorHelper.getInstance().getCurrentMotorId() == null) {
//          无摩托车标记
            mRelayout_motor_basic_info.setVisibility(View.INVISIBLE);
            initListener1();
        } else {
//          有摩托车标记
            mRelayout_motor_basic_info.setVisibility(View.VISIBLE);
            showMotorView();
            initListener2();
        }
    }

    private void showMotorView() {
//        刷新当前摩托车信息
        boolean isRefresh = MotorHelper.getInstance().refreshCurrentMotor(getContext());
        if (!isRefresh) {
            return;
        }

        mMotorBean = MotorHelper.getInstance().getCurrentMotor();
//        设置页面摩托车信息
        if (mMotorBean.getUrl() != null) {
            Glide.with(getActivity()).load(mMotorBean.getUrl()).placeholder(R.mipmap.network_loading).error(R.mipmap.logo).dontAnimate().into(mIv_motor);
        }
        mTv_warranty_period.setText("保修期剩余：" + mMotorBean.getWarrantyDays() + "天/" + mMotorBean.getWarrantyDistance() + "公里");
        mTv_model.setText(mMotorBean.getModel());
        mTv_year.setText(mMotorBean.getYear() + "");
        mTv_type.setText(mMotorBean.getType());

    }

    /**
     * 未添加摩托车时的点击事件
     */
    private void initListener1() {
        mIv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQRPickDialog(getActivity(), false);   //弹出Dialog
            }
        });
//        定位
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BindFirstActivity.class));
            }
        });
//        切换
        mChange_motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BindFirstActivity.class));
            }
        });

        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddMotorActivity.class));
            }
        });
//        维修服务
        mRepairApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BindFirstActivity.class);
                startActivity(intent);
            }
        });
    }

    //    有摩托车标记时的点击事件
    private void initListener2() {
        mIv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQRPickDialog(getActivity(), true);   //弹出Dialog
            }
        });

//        定位
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });
//        切换
        mChange_motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyMotorActivity.class));
            }
        });
//        维修服务
        mRepairApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RepairApplicationActivity.class);
                startActivity(intent);
            }
        });
        currentMotorId = MotorHelper.getInstance().getCurrentMotorId();

//        摩托车信息
        mRelayout_motor_basic_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScrollingActivity.class);
                intent.putExtra("motor", mMotorBean);
                startActivity(intent);

            }
        });
    }

    /**
     * 弹出设置头像图片的Dialog
     *
     * @param activity
     */
    public void showQRPickDialog(final Activity activity, final boolean isBind) {
        String[] items = new String[]{"扫一扫", "摩托车二维码"};
        new AlertDialog.Builder(activity)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
//                                选择扫一扫
                                boolean qrCodePermission = PermissionUtils.getQRCodePermission(activity);
                                if (qrCodePermission) {
                                    Log.d(TAG, "onClick: 已有权限,直接跳转");
                                    Intent intent = new Intent(activity, CaptureActivity.class);
                                    startActivityForResult(intent, 100);
                                }
                                break;
//                                选择二维码
                            case 1:
                                if (isBind) {
                                    Intent intent = new Intent(activity, QRCodeActivity.class);
                                    intent.putExtra("currentMotorId", currentMotorId);
                                    startActivity(intent);
                                }else{
                                    startActivity(new Intent(getActivity(), BindFirstActivity.class));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                Log.d(TAG, "onActivityResult: 扫描二维码/条码回传");
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.d(TAG, "onActivityResult: 扫描结果为" + content);
//                连接服务器，根据摩托车id获取摩托车信息
                if (content != null && content != "") {
                    MotorBean motorBean = ClientUtils.getCurrentMotor(getContext(), content);
                    Log.d(TAG, "onActivityResult: 获取的摩托车信息：" + motorBean.toString());
                    if (motorBean == null || motorBean.getId() == null) {
                        Log.d(TAG, "获取数据失败: motorItem==null");
                        Toast.makeText(getContext(), "扫描结果为" + content, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "run: 扫码成功");
                        motorBean.setStatus(0);
                        Toast.makeText(getContext(), "扫描成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ScrollingActivity.class);
                        intent.putExtra("motor", motorBean);
                        startActivity(intent);
                    }
                } else {
                    Log.d(TAG, "onActivityResult: 扫码结果无效");
                }
            }
        }
//        动态获取权限回调
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initView(mView);
        Log.d(TAG, "onResume: 数据回显");
    }
}
