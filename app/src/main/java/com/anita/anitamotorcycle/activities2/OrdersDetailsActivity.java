package com.anita.anitamotorcycle.activities2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.beans.RepairmanBean;
import com.anita.anitamotorcycle.beans.UserBean;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.hb.dialog.dialog.ConfirmDialog;

public class OrdersDetailsActivity extends AppCompatActivity {
    private ImageView mBack;
    private String mRecordID;
    private RecordBean mRecordBean;
    private TextView mTv_doit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);

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

//        受理、完成
        mTv_doit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info;
                if (mRecordBean.getRepair_status() == 1) {
                    info = "确认受理订单" + mRecordBean.getId() + "吗？";
                    showConfirmDialog(info);
                } else if (mRecordBean.getRepair_status() == 2) {
                    info = "确认订单" + mRecordBean.getId() + "正在维修中吗？";
                    showConfirmDialog(info);
                } else if (mRecordBean.getRepair_status() == 3) {
                    info = "请选择订单" + mRecordBean.getId() + "维修完成情况？";
                    showCompleteDialog(info);
                }
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mTv_doit = findViewById(R.id.tv_doit);
        LinearLayout ll_orders = findViewById(R.id.ll_orders);
        //获取上一页面传递的数据
        mRecordBean = (RecordBean) getIntent().getSerializableExtra("record");

        // TODO: 2020/2/22 车辆定位获取实时定位

//        维修单信息
        TextView tv_repair_id = findViewById(R.id.tv_repair_id);
        TextView tv_create_at = findViewById(R.id.tv_create_at);
        TextView tv_repair_status = findViewById(R.id.tv_repair_status);
        TextView tv_update_at = findViewById(R.id.tv_update_at);
        tv_repair_id.setText("维修单号：" + mRecordBean.getId());
        tv_create_at.setText("申请时间：" + mRecordBean.getCreate_at());
        tv_repair_status.setText("当前状态：" + mRecordBean.getRepairStatus());
        tv_update_at.setText("更新时间：" + mRecordBean.getUpdate_at());

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

        if (mRecordBean.getRepair_status() == 1) {
//            提交成功1
            mTv_doit.setVisibility(View.VISIBLE);
            mTv_doit.setText("受理");
            ll_orders.setVisibility(View.GONE);
        } else if (mRecordBean.getRepair_status() == 2 || mRecordBean.getRepair_status() == 3) {
//            维修中
            mTv_doit.setText("完成");
            mTv_doit.setVisibility(View.VISIBLE);
            ll_orders.setVisibility(View.VISIBLE);
        } else {
//            维修完成
            mTv_doit.setVisibility(View.GONE);
            ll_orders.setVisibility(View.VISIBLE);
        }
    }

    private void showConfirmDialog(String info) {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg(info);

        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
//                连接服务器，update订单状态
                if (mRecordBean.getRepair_status() == 1) {
//                    传回维修员phone
                    mRecordBean.setRepairman_id(UserHelper.getInstance().getPhone());
                    mRecordBean.setRepair_status(mRecordBean.getRepair_status() + 1);
                    mRecordBean.setUpdate_at(UserUtils.getCurrentTime());
                    boolean isUpdate = ClientUtils.updateRecord(mRecordBean);
                    if (isUpdate) {
                        Toast.makeText(getApplicationContext(), "受理成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(OrdersDetailsActivity.this, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mRecordBean.setRepair_status(mRecordBean.getRepair_status() + 1);
                    mRecordBean.setUpdate_at(UserUtils.getCurrentTime());
                    boolean isUpdate = ClientUtils.updateRecord(mRecordBean);
                    if (isUpdate) {
                        Toast.makeText(getApplicationContext(), "处理成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(OrdersDetailsActivity.this, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                    }
                }

                onBackPressed();    //后退操作
            }

            @Override
            public void cancel() {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
    }

    private void showCompleteDialog(String info){
        AlertDialog dialog2 = new AlertDialog.Builder (this).create ();
        dialog2.setTitle ("维修完成情况");
        dialog2.setMessage (info);
        dialog2.setButton (DialogInterface.BUTTON_NEGATIVE, "维修失败", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRecordBean.setRepair_status(7);
                mRecordBean.setUpdate_at(UserUtils.getCurrentTime());
                boolean isUpdate = ClientUtils.updateRecord(mRecordBean);
                if (isUpdate) {
                    Toast.makeText(getApplicationContext(), "处理成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OrdersDetailsActivity.this, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();    //后退操作
            }
        });
        dialog2.setButton (DialogInterface.BUTTON_POSITIVE, "维修成功", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRecordBean.setRepair_status(mRecordBean.getRepair_status() + 1);
                mRecordBean.setUpdate_at(UserUtils.getCurrentTime());
                boolean isUpdate = ClientUtils.updateRecord(mRecordBean);
                if (isUpdate) {
                    Toast.makeText(getApplicationContext(), "处理成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OrdersDetailsActivity.this, "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();    //后退操作
            }
        });
        //一定要调用show（）方法，否则对话框不会显示
        dialog2.show ();

    }
}
