package com.anita.anitamotorcycle.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.MotorDetailsActivity;
import com.anita.anitamotorcycle.activities.ScrollingActivity;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.fragments.HomeFragment;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;


/**
 * @author Anita
 * @description:我的摩托车数据适配器
 * @date : 2020/1/17 17:21
 */
public class MyMotorDataAdapter extends RecyclerView.Adapter<MyMotorDataAdapter.InnerHolder> {
    private static final String TAG = "MyMotorDataAdapter";
    private Activity context;
    private List<MotorBean> mData;
    private View mView;

    /**
     * 使用构造函数传递数据
     *
     * @param data
     */
    public MyMotorDataAdapter(List<MotorBean> data, Activity context) {
        this.mData = data;
        this.context = context;
    }

    public void setData(List<MotorBean> data) {
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
        mView = View.inflate(parent.getContext(), R.layout.item_my_motor, null);
        return new InnerHolder(mView);
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
        final MotorBean motorBean = mData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: holder.itemView.setOnClickListener....test1");
                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                intent.putExtra("motor",motorBean);
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
        private ImageView mIv_my_motor;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
//            找到控件
            mIv_my_motor = itemView.findViewById(R.id.iv_my_motor);
            mPlateNumbers = itemView.findViewById(R.id.tv_plate_numbers);
            mMotorModel = itemView.findViewById(R.id.tv_motor_model);
            mFactory = itemView.findViewById(R.id.tv_factory);
            mWarranty = itemView.findViewById(R.id.tv_warranty);
        }

        public void setData(MotorBean motorBean) {
            if (motorBean.getUrl() != null) {
                Glide.with(context).load(motorBean.getUrl()).placeholder(R.mipmap.network_loading).error(R.mipmap.logo).dontAnimate().into(mIv_my_motor);
            }
//
            mPlateNumbers.setText(motorBean.getPlate_numbers());
            mMotorModel.setText("车辆型号：" + motorBean.getModel());
            mFactory.setText("品牌名称：" + motorBean.getBrand());
            mWarranty.setText("保修期剩余：" + motorBean.getWarrantyDays() + "天/" + motorBean.getWarrantyDistance() + "公里");

        }


    }
}
