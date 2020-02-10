package com.anita.anitamotorcycle.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.MotorDetailsActivity;
import com.anita.anitamotorcycle.beans.MotorItem;

import java.util.List;


/**
 * @author Anita
 * @description:我的摩托车数据适配器
 * @date : 2020/1/17 17:21
 */
public class MyMotorDataAdapter extends RecyclerView.Adapter<MyMotorDataAdapter.InnerHolder> {
    private static final String TAG = "MyMotorDataAdapter";
    private List<MotorItem> mData;

    /**
     * 使用构造函数传递数据
     *
     * @param data
     */
    public MyMotorDataAdapter(List<MotorItem> data) {
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
        View view = View.inflate(parent.getContext(), R.layout.item_my_motor, null);
        return new InnerHolder(view);
    }

    /**
     * 用于绑定holder，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final InnerHolder holder, int position) {
        holder.setData(mData.get(position));
        final MotorItem motorItem = mData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: holder.itemView.setOnClickListener....test1");
                Intent intent = new Intent(v.getContext(), MotorDetailsActivity.class);
                intent.putExtra("plateNumbers", motorItem.getPlate_numbers());
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

        private TextView mPlateNumbers;
        private TextView mMotorModel;
        private TextView mFactory;
        private TextView mWarranty;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
//            找到控件
            mPlateNumbers = itemView.findViewById(R.id.tv_plate_numbers);
            mMotorModel = itemView.findViewById(R.id.tv_motor_model);
            mFactory = itemView.findViewById(R.id.tv_factory);
            mWarranty = itemView.findViewById(R.id.tv_warranty);
        }

        public void setData(MotorItem motorItem) {
            mPlateNumbers.setText(motorItem.getPlate_numbers());
            mMotorModel.setText(motorItem.getModel());
            mFactory.setText(motorItem.getBrand());
            mWarranty.setText("保修期：" + motorItem.getBuy_at() + "天/" + motorItem.getWarranty_distance() + "公里");
        }
    }
}
