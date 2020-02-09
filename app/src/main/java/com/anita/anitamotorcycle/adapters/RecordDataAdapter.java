package com.anita.anitamotorcycle.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.RepairDetailsActivity;
import com.anita.anitamotorcycle.beans.RecordItem;

import java.util.List;

/**
 * @author Anita
 * @description:维修记录数据适配器
 * @date : 2020/1/17 17:21
 */
public class RecordDataAdapter extends RecyclerView.Adapter<RecordDataAdapter.InnerHolder> {
    private static final String TAG = "RecordDataAdapter";
    private List<RecordItem> mData;
    public RecordDataAdapter(){}


    /**
     * 使用构造函数传递数据
     *
     * @param data
     */
    public RecordDataAdapter(List<RecordItem> data) {
        this.mData = data;
    }

    public void setData(List<RecordItem> recordList) {
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
        final RecordItem recordItem = mData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: holder.itemView.setOnClickListener....test1");
                Intent intent = new Intent(v.getContext(), RepairDetailsActivity.class);
                intent.putExtra("recordPlateNumbers", recordItem.getPlate_numbers());
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
        private Button mEvaluate;
        private Button mConfirm;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
//            找到控件
            mMotorType = itemView.findViewById(R.id.tv_motor_type);
            mRepairStatus = itemView.findViewById(R.id.tv_repair_status);
            mUpdateTime = itemView.findViewById(R.id.tv_update_time_data);
            mFactoryName = itemView.findViewById(R.id.tv_factory_name_data);
            mTroubleType = itemView.findViewById(R.id.tv_trouble_type_data);
            mEvaluate = itemView.findViewById(R.id.btn_evaluate);
            mEvaluate.setVisibility(View.GONE);
            mConfirm = itemView.findViewById(R.id.btn_confirm);
        }

        /**
         * 设置数据
         *
         * @param recordItem
         */
        public void setData(RecordItem recordItem) {
            mMotorType.setText(recordItem.getPlate_numbers());
            mRepairStatus.setText(recordItem.getRepair_status());
            mUpdateTime.setText(recordItem.getUpdate_at());
            mFactoryName.setText(recordItem.getFactory_name());
            mTroubleType.setText(recordItem.getProblem_type());
        }
    }
}
