package com.anita.anitamotorcycle.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.MotorItemBean;

import java.util.List;


/**
 * @author Anita
 * @description:我的摩托车数据适配器
 * @date : 2020/1/17 17:21
 */
public class MyMotorDataAdapter extends RecyclerView.Adapter<MyMotorDataAdapter.InnerHolder> {

    private List<MotorItemBean> mData;

    /**
     * 使用构造函数传递数据
     * @param data
     */
    public MyMotorDataAdapter(List<MotorItemBean> data){
        this.mData =data;
    }

    /**
     * 用于创建条目view,即条目的界面
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_my_motor,null);
        return new InnerHolder(view);
    }

    /**
     * 用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    /**
     * 返回条目个数
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
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(MotorItemBean motorItemBean) {
        }
    }
}
