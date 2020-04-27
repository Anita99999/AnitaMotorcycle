package com.anita.anitamotorcycle.activities2;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.activities.AddMotorActivity;
import com.anita.anitamotorcycle.activities.BaseActivity;
import com.anita.anitamotorcycle.adapters.MyMotorDataAdapter;
import com.anita.anitamotorcycle.adapters.OrdersDataAdapter;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.MotorUtils;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;


/**
 * 待处理订单
 */
public class TodoOrdersActivity extends BaseActivity {
    private static final String TAG = "TodoOrdersActivity";

    private RecyclerView mRv_orders;
    private List<RecordBean> mDatas;
    private ImageView mBack;
    private OrdersDataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_orders);

        initView();
        initListener();
        showList(); //实现list
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });


    }


    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mRv_orders = findViewById(R.id.rv_orders);
        LinearLayout ll_empty = findViewById(R.id.ll_empty);

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
        mDatas = ClientUtils.getTohandList();
        Log.d(TAG, "getTohandList: mDatas--" + mDatas);

    }

    private void showList() {
//        设置recyclerview样式，设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
        mAdapter = new OrdersDataAdapter(mDatas);
//        设置adaptor到recyclerview里
        mRv_orders.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<RecordBean> refreshDatas;
        Log.d(TAG, "onResume: 回显");
//        mDatas.clear(); //去掉之前的数据
        mDatas = ClientUtils.getTohandList();
        mAdapter.setData(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }


}
