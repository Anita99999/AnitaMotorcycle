package com.anita.anitamotorcycle.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities2.OrdersDetailsActivity;
import com.anita.anitamotorcycle.beans.RecordBean;

import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author Anita
 * @description:订单记录数据适配器
 * @date : 2020/1/17 17:21
 */
public class OrdersDataAdapter extends RecyclerView.Adapter<OrdersDataAdapter.InnerHolder> {
    private static final String TAG = "OrdersDataAdapter";
    private List<RecordBean> mData;

    public OrdersDataAdapter() {
    }


    /**
     * 使用构造函数传递数据
     *
     * @param data
     */
    public OrdersDataAdapter(List<RecordBean> data) {
        this.mData = data;
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
        View view = View.inflate(parent.getContext(), R.layout.item_order, null);
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
                Intent intent = new Intent(v.getContext(), OrdersDetailsActivity.class);
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

        private TextView mRepairStatus;
        private TextView mUpdateTime;
        private TextView mTv_repairman_name;
        private TextView mTv_repairman_phone;
        private TextView mTv_problem_type_data;
        private TextView mTv_record_id;
        private TextView mTv_position;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
//            找到控件
            mTv_record_id = itemView.findViewById(R.id.tv_record_id);
            mRepairStatus = itemView.findViewById(R.id.tv_repair_status);

            mUpdateTime = itemView.findViewById(R.id.tv_update_time_data);
            mTv_repairman_name = itemView.findViewById(R.id.tv_repairman_name);
            mTv_repairman_phone = itemView.findViewById(R.id.tv_repairman_phone);
            mTv_problem_type_data = itemView.findViewById(R.id.tv_problem_type_data);
            mTv_position = itemView.findViewById(R.id.tv_position);

        }

        /**
         * 设置数据
         *
         * @param recordBean
         */
        public void setData(RecordBean recordBean) {
            mTv_record_id.setText(recordBean.getId());

            if (recordBean.getRepair_status() == 1) {
//                待处理
                mRepairStatus.setText("待处理");

            } else if (recordBean.getRepair_status() >= 2 && recordBean.getRepair_status() <= 4) {
//                待维修
                mRepairStatus.setText(recordBean.getRepairStatus());
            } else {
//                已完成
                mRepairStatus.setText(recordBean.getRepairStatus());
            }
            mUpdateTime.setText(recordBean.getUpdate_at());

            mTv_repairman_name.setText(recordBean.getUser_name());

            final String phone = recordBean.getPhone();
            mTv_repairman_phone.setText(phone);
            mTv_repairman_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });
            mTv_problem_type_data.setText(recordBean.getProblem_type());
            mTv_position.setText(recordBean.getPosition());
        }


    }
}
