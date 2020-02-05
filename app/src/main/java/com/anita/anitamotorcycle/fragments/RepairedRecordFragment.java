package com.anita.anitamotorcycle.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.RecordDataAdapter;
import com.anita.anitamotorcycle.beans.RecordItem;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Anita
 * @description:维修记录中的已完成 * @date : 2020/1/5 22:49
 */
public class RepairedRecordFragment extends Fragment {

    private RecyclerView mRepairedList;
    private List<RecordItem> mDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repairing_record, container, false);
        mRepairedList = view.findViewById(R.id.rv_repairing_record);
        getData();  //获取数据
        showList(true); //实现list
        return view;
    }

    /*
   TODO：从网络中获取数据，暂时模拟数据
    */
    private void getData() {
//        创建数据集合
        mDatas = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String dateFormat = sdf.format(date);// new Date()为获取当前系统时间
//        创建模拟数据
        for (int i = 1; i <= 6; i++) {
//            创建数据对象
            RecordItem data = new RecordItem();
            data.repairStatus = "提交成功" + i;
            data.plateNumbers = "车牌号" + i;
            data.updateAt = dateFormat;
            data.factoryName = "商家名" + i;
            data.troubleType = "故障类型" + i;
//            添加到集合里
            mDatas.add(data);
        }
    }

    private void showList(boolean isReverse) {
//        设置recyclerview样式，设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        设置反向
//        layoutManager.setReverseLayout(isReverse);
        mRepairedList.setFocusable(false);
        mRepairedList.setLayoutManager(layoutManager);
        mRepairedList.setNestedScrollingEnabled(false);
        //        item间距
        mRepairedList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 8);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 8);
                outRect.left = UIUtil.dip2px(view.getContext(), 12);
                outRect.right = UIUtil.dip2px(view.getContext(), 12);
            }
        });

        //        创建适配器
        RecordDataAdapter adapter = new RecordDataAdapter(mDatas);
//        设置adaptor到recyclerview里
        mRepairedList.setAdapter(adapter);
    }


}
