package com.anita.anitamotorcycle.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.RecordItemBean;

import java.util.List;

/**
 * @author Anita
 * @description:维修记录数据适配器
 * @date : 2020/1/17 17:21
 */
public class RecordDataAdapter extends RecyclerView.Adapter<RecordDataAdapter.InnerHolder> {
    private List<RecordItemBean> mData;

    /**
     * 使用构造函数传递数据
     * @param data
     */
    public RecordDataAdapter(List<RecordItemBean> data) {
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
         * @param recordItemBean
         */
        public void setData(RecordItemBean recordItemBean) {
            mMotorType.setText(recordItemBean.plateNumbers);
            mRepairStatus.setText(recordItemBean.repairStatus);
            mUpdateTime.setText(recordItemBean.updateAt.toString());
            mFactoryName.setText(recordItemBean.factoryName);
            mTroubleType.setText(recordItemBean.troubleType);
        }
    }
}
