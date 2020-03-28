package com.anita.anitamotorcycle.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.AddMotorNumActivity;
import com.anita.anitamotorcycle.activities.RepairDetailsActivity;
import com.anita.anitamotorcycle.activities.RepairRecordActivity;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.hb.dialog.dialog.ConfirmDialog;

import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.getActivityByContext;
import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author Anita
 * @description:维修记录数据适配器
 * @date : 2020/1/17 17:21
 */
public class RecordDataAdapter extends RecyclerView.Adapter<RecordDataAdapter.InnerHolder> {
    private static final String TAG = "RecordDataAdapter";
    private List<RecordBean> mData;

    public RecordDataAdapter() {
    }


    /**
     * 使用构造函数传递数据
     *
     * @param data
     */
    public RecordDataAdapter(List<RecordBean> data) {
        this.mData = data;
    }

    public void setData(List<RecordBean> recordList) {
        this.mData = recordList;
        //更新UI。
        notifyDataSetChanged();
    }

    /**
     * 用于创建条目view,即条目的界面
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_record, null);
        return new InnerHolder(view);
    }

    /**
     * 用于绑定holder，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(mData.get(position));
        final RecordBean recordBean = mData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: holder.itemView.setOnClickListener....test1");
                Intent intent = new Intent(v.getContext(), RepairDetailsActivity.class);
                intent.putExtra("record", recordBean);
                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * 返回条目个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView mMotorType;
        private TextView mRepairStatus;
        private TextView mUpdateTime;
        private TextView mFactoryName;
        private TextView mTroubleType;
        private Button mCancel;
        private Button mConfirm;
        private final RelativeLayout mRl_repairman;
        private final TextView mTv_repairman_name;
        private final TextView mTv_repairman_phone;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
//            找到控件
            mMotorType = itemView.findViewById(R.id.tv_motor_type);
            mRepairStatus = itemView.findViewById(R.id.tv_repair_status);

            mUpdateTime = itemView.findViewById(R.id.tv_update_time_data);
            mFactoryName = itemView.findViewById(R.id.tv_factory_name_data);
            mRl_repairman = itemView.findViewById(R.id.rl_repairman);
            mTv_repairman_name = itemView.findViewById(R.id.tv_repairman_name);
            mTv_repairman_phone = itemView.findViewById(R.id.tv_repairman_phone);
            mTroubleType = itemView.findViewById(R.id.tv_trouble_type_data);

            mCancel = itemView.findViewById(R.id.btn_cancel);
            mConfirm = itemView.findViewById(R.id.btn_confirm);
        }

        /**
         * 设置数据
         *
         * @param recordBean
         */
        public void setData(final RecordBean recordBean) {
            mCancel.setVisibility(View.GONE);
            mConfirm.setVisibility(View.GONE);
            if (recordBean.getRepair_status() == 1 || recordBean.getRepair_status() == 6) {
//            维修员未接单：1提交成功、6维修取消
                if (recordBean.getRepair_status() == 1) {
                    mCancel.setVisibility(View.VISIBLE);
                    mFactoryName.setText("等待接单..");
                }else{
                    mFactoryName.setText("未接单");
                }
                mRl_repairman.setVisibility(View.GONE);
            } else {
//            维修员已接单：2分派维修员中、3维修中、4维修完成、5维修成功、7维修失败
                if(recordBean.getRepair_status() == 4){
                    mConfirm.setVisibility(View.VISIBLE);
                }
//                维修员信息
                mRl_repairman.setVisibility(View.VISIBLE);
                mTv_repairman_name.setText(recordBean.getRepairman_name());
                final String phone = recordBean.getRepairman_phone();
                mTv_repairman_phone.setText(phone);
                mTv_repairman_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                mFactoryName.setText(recordBean.getFactory_name());
            }

            mMotorType.setText(recordBean.getPlate_numbers());
            mRepairStatus.setText(recordBean.getRepairStatus());
            mUpdateTime.setText(recordBean.getUpdate_at());

            mTroubleType.setText(recordBean.getProblem_type());
//            取消维修
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick:recordBean-- " + recordBean);
                    showComfirmDialog(v, recordBean);
                }
            });
//            确认取车
            mConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showComfirmDialog1(v, recordBean);
                }


            });
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

                }

                @Override
                public void cancel() {
                    Toast.makeText(v.getContext(), "取消", Toast.LENGTH_LONG).show();
                }
            });
            confirmDialog.show();
        }
    }
}
