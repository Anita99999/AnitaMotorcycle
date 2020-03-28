package com.anita.anitamotorcycle.activities;

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
import com.anita.anitamotorcycle.adapters.MyMotorDataAdapter;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.utils.MotorUtils;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;


/**
 * 我的摩托车
 */
public class MyMotorActivity extends BaseActivity {
    private static final String TAG = "MyMotorActivity";

    private MyMotorDataAdapter mDataAdapter;
    private RecyclerView mRv_my_motor;
    private List<MotorBean> mDatas = null;
    private ImageView mBack;
    private TextView mAddMotor;
    private LinearLayout mLl_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motor);

        initView();
        initListener();
        mDatas = MotorUtils.getMotorsData(this);    //获取数据
        showList(); //实现list
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

        mAddMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMotorActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mRv_my_motor = findViewById(R.id.rv_my_motor);
        mAddMotor = findViewById(R.id.tv_add_motor);
        mLl_empty = findViewById(R.id.ll_empty);

        if (MotorHelper.getInstance().getCurrentMotorId() == null) {
//          无摩托车标记
            mRv_my_motor.setVisibility(View.INVISIBLE);
            mLl_empty.setVisibility(View.VISIBLE);
        } else {
//          有摩托车标记
            mRv_my_motor.setVisibility(View.VISIBLE);
            mLl_empty.setVisibility(View.INVISIBLE);
        }
    }

    private void showList() {
//        设置recyclerview样式，设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRv_my_motor.setLayoutManager(layoutManager);
//        mRv_my_motor.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRv_my_motor.setNestedScrollingEnabled(false);
//        item间距
        mRv_my_motor.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 8);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 8);
                outRect.left = UIUtil.dip2px(view.getContext(), 12);
                outRect.right = UIUtil.dip2px(view.getContext(), 12);
            }
        });
//        创建适配器
        mDataAdapter = new MyMotorDataAdapter(mDatas,this);
//        设置adaptor到recyclerview里
        mRv_my_motor.setAdapter(mDataAdapter);
    }


}
