package com.anita.anitamotorcycle.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.OrdersDataAdapter;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * @author Anita
 * @description:订单的待维修
 * * @date : 2020/1/5 22:49
 */
public class ToRepairFragment extends Fragment {
    private static final String TAG = "ToRepairFragment";

    private RecyclerView mRv_orders;
    private List<RecordBean> mDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: 待维修1");
        View view = inflater.inflate(R.layout.fragment_to_hand, container, false);
        initView(view);

        showList(); //实现list
        return view;
    }

    private void initView(View view) {
        mRv_orders = view.findViewById(R.id.rv_orders);
        LinearLayout ll_empty = view.findViewById(R.id.ll_empty);

        getData();  //获取数据
        if (mDatas == null || mDatas.size() == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            mRv_orders.setVisibility(View.GONE);
            Log.d(TAG, "initView: mDatas1--" + mDatas);
        } else {
            ll_empty.setVisibility(View.GONE);
            mRv_orders.setVisibility(View.VISIBLE);
            Log.d(TAG, "initView: mDatas2--" + mDatas);
        }
    }


    /*
    TODO：从网络中获取数据
     */
    private void getData() {
//        创建数据集合
//        创建数据集合
        mDatas = ClientUtils.getToRepairList(UserHelper.getInstance().getPhone(),0);
        Log.d(TAG, "getToRepairList: mDatas--" + mDatas);

    }

    private void showList() {
//        设置recyclerview样式，设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        设置反向
//        layoutManager.setReverseLayout(isReverse);
        mRv_orders.setFocusable(false);
        mRv_orders.setLayoutManager(layoutManager);
//        item间距
        mRv_orders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 8);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 8);
                outRect.left = UIUtil.dip2px(view.getContext(), 12);
                outRect.right = UIUtil.dip2px(view.getContext(), 12);
            }
        });
        mRv_orders.setNestedScrollingEnabled(false);

//        创建适配器
        OrdersDataAdapter adapter = new OrdersDataAdapter(mDatas);
//        设置adaptor到recyclerview里
        mRv_orders.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 待维修3");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 待维修2");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 待维修4");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 待维修5");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: 待维修6");
    }
}
