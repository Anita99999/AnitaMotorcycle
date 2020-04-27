package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.hb.dialog.dialog.ConfirmDialog;

import java.io.Serializable;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class RepairDetailsActivity extends AppCompatActivity {
    private static final String TAG = "RepairDetailsActivity";

    private ImageView mBack;
    private LinearLayout mLl_info2;
    private String mRecordID;
    private RecordBean mRecordBean;
    private Button mBtn_cancel;
    private Button mBtn_confirm;
    private ImageView mIv_qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_details);

        initView();
        initListener();
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComfirmDialog(v, mRecordBean);

            }
        });
//            确认取车
        mBtn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComfirmDialog1(v, mRecordBean);

            }
        });

    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mLl_info2 = findViewById(R.id.ll_info2);

        mBtn_cancel = findViewById(R.id.btn_cancel);
        mBtn_cancel.setVisibility(View.GONE);
        mBtn_confirm = findViewById(R.id.btn_confirm);
        mBtn_confirm.setVisibility(View.GONE);
        //获取上一页面传递的数据
        mRecordBean = (RecordBean) getIntent().getSerializableExtra("record");

        if (mRecordBean.getRepair_status() == 1 || mRecordBean.getRepair_status() == 6) {
            if (mRecordBean.getRepair_status() == 1) {
                mBtn_cancel.setVisibility(View.VISIBLE);
            }
//            维修员未接单
            mLl_info2.setVisibility(View.GONE);
        } else {
            if (mRecordBean.getRepair_status() == 4) {
                mBtn_confirm.setVisibility(View.VISIBLE);
            }
//            维修员已接单
            mLl_info2.setVisibility(View.VISIBLE);
            //        维修商信息
            TextView tv_repairman_name = findViewById(R.id.tv_repair_name);
            TextView tv_repairman_phone = findViewById(R.id.tv_repairman_phone);
            mIv_qq = findViewById(R.id.iv_qq);
            TextView tv_factory_name = findViewById(R.id.tv_factory_name);
            TextView tv_factory_address = findViewById(R.id.tv_factory_address);
            tv_repairman_name.setText("维修员姓名：" + mRecordBean.getRepairman_name());
            tv_repairman_phone.setText(mRecordBean.getRepairman_phone());
            tv_repairman_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mRecordBean.getRepairman_phone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });
            //            跳转到qq会话界面
            mIv_qq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecordBean.getRepairman_qq() != null && mRecordBean.getRepairman_qq() != "") {
                        goQQChat(mRecordBean.getRepairman_qq());
                    } else {
                        Log.d(TAG, "onClick: mRecordBean.getRepairman_qq()==" + mRecordBean.getRepairman_qq());
                    }
                }
            });
            tv_factory_name.setText("维修商名称：" + mRecordBean.getFactory_name());
            tv_factory_address.setText("维修商地址：" + mRecordBean.getFactory_address());
        }
//        维修单信息
        TextView tv_repair_id = findViewById(R.id.tv_repair_id);
        TextView tv_create_at = findViewById(R.id.tv_create_at);
        TextView tv_repair_status = findViewById(R.id.tv_repair_status);
        TextView tv_update_at = findViewById(R.id.tv_update_at);
        tv_repair_id.setText("维修单号：" + mRecordBean.getId());
        tv_create_at.setText("提交申请时间：" + mRecordBean.getCreate_at());
        tv_repair_status.setText("当前维修状态：" + mRecordBean.getRepairStatus());
        tv_update_at.setText("状态更新时间：" + mRecordBean.getUpdate_at());

//        维修详情
        TextView tv_user_name = findViewById(R.id.tv_user_name);
        TextView tv_phone = findViewById(R.id.tv_phone);
        TextView tv_plate_numbers = findViewById(R.id.tv_plate_numbers);
        TextView tv_position = findViewById(R.id.tv_position);
        TextView tv_problem_type = findViewById(R.id.tv_problem_type);
        TextView tv_description = findViewById(R.id.tv_description);
        tv_user_name.setText("申请人：" + mRecordBean.getUser_name());
        tv_phone.setText("联系电话：" + mRecordBean.getPhone());
        tv_plate_numbers.setText("车牌号：" + mRecordBean.getPlate_numbers());
        tv_position.setText("车辆位置：" + mRecordBean.getPosition());
        tv_problem_type.setText("故障类型：" + mRecordBean.getProblem_type());
        tv_description.setText("故障描述：" + mRecordBean.getDescription());


    }

    private void showComfirmDialog(final View v, final RecordBean record) {
        ConfirmDialog confirmDialog = new ConfirmDialog(v.getContext());
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认取消" + record.getPlate_numbers() + "车辆的维修申请吗？");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
//                连接数据库，update record信息
                record.setRepair_status(6);
                record.setUpdate_at(UserUtils.getCurrentTime());
                boolean isUpdate = ClientUtils.updateRecord(record);
                if (isUpdate) {
                    Toast.makeText(v.getContext(), "取消成功", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(v.getContext(), "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();    //后退操作
            }

            @Override
            public void cancel() {
                Toast.makeText(v.getContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
    }

    private void showComfirmDialog1(final View v, final RecordBean record) {
        ConfirmDialog confirmDialog = new ConfirmDialog(v.getContext());
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认车辆" + record.getPlate_numbers() + "维修完成并取车吗？");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
//                连接数据库，update record信息
                record.setRepair_status(5);
                record.setUpdate_at(UserUtils.getCurrentTime());
                boolean isUpdate = ClientUtils.updateRecord(record);
                if (isUpdate) {
                    Toast.makeText(v.getContext(), "取车成功", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(v.getContext(), "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();    //后退操作
            }

            @Override
            public void cancel() {
                Toast.makeText(v.getContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
    }

    private void goQQChat(String key) {
        try {
            String str = "mqqwpa://im/chat?chat_type=wpa&uin=" + key;
            Log.d(TAG, "goQQChat: str==" + str);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
        } catch (Exception e) {
            Log.d(TAG, "goQQChat:未安装qq ");
            Toast.makeText(RepairDetailsActivity.this, "请先安装qq", Toast.LENGTH_SHORT).show();
        }
    }
}
