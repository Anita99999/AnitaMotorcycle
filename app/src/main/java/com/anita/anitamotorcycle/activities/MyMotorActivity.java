package com.anita.anitamotorcycle.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.MyMotorDataAdapter;
import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.interfaces.IMyMotorViewCallback;
import com.anita.anitamotorcycle.presenters.MyMotorPresenter;
import com.anita.anitamotorcycle.views.UILoaderView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mob.MobSDK.getContext;

/**
 * 我的摩托车
 */
public class MyMotorActivity extends BaseActivity implements IMyMotorViewCallback {
    private static final String TAG = "MyMotorActivity";

    private View mRootView;
    private RecyclerView mMyMototList;
    private List<MotorItem> mDatas;
    private ImageView mBack;
    private TextView mAddMotor;
    private MyMotorPresenter mMyMotorPresenter;
    private MyMotorDataAdapter mDataAdapter;
    private UILoaderView mUiLoaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motor);
//        找到控件


        initView();
        initListener();

        //获取逻辑层的对象
        mMyMotorPresenter = MyMotorPresenter.getInstance();
        //先设置通知接口的ui注册
        mMyMotorPresenter.registerViewCallback(this);
//        获取摩托车列表,获取数据
        mMyMotorPresenter.getMyMotorList();
//        showList(); //实现list

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
        mMyMototList = findViewById(R.id.rv_my_motor);
        mAddMotor = findViewById(R.id.tv_add_motor);
    }

    private void showList(List<MotorItem> datas) {
//        设置recyclerview样式，设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMyMototList.setLayoutManager(layoutManager);
//        mMyMototList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mMyMototList.setNestedScrollingEnabled(false);
//        item间距
        mMyMototList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 8);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 8);
                outRect.left = UIUtil.dip2px(view.getContext(), 12);
                outRect.right = UIUtil.dip2px(view.getContext(), 12);
            }
        });
//        创建适配器
        mDataAdapter = new MyMotorDataAdapter(datas);
//        设置adaptor到recyclerview里
        mMyMototList.setAdapter(mDataAdapter);
    }

    /**
     * 把数据设置给适配器，并且更新UI
     * 当获取到摩托车数据时，该方法才被调用
     *
     * @param result
     */
    @Override
    public void onMyMotorListLoaded(List<MotorItem> result) {
        Log.d(TAG, "onMyMotorListLoaded: ");
//        mDataAdapter.setData(result);
        showList(result);
    }

    /**
     * 网络错误
     */
    @Override
    public void onNetworkError() {
        Log.d(TAG, "onNetworkError: ");
        setContentView(R.layout.fragment_error_view);
    }

    /**
     * 数据为空
     */
    @Override
    public void onEmpty() {
        Log.d(TAG, "onEmpty: ");
        setContentView(R.layout.fragment_empty_view);
    }

    /**
     * 正在加载
     */
    @Override
    public void onLoading() {
        Log.d(TAG, "onLoading: ");
        setContentView(R.layout.fragment_loading_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        取消接口注册，以免内存泄露
        if (mMyMotorPresenter != null) {
            mMyMotorPresenter.unRegisterViewCallback(this);
        }
    }
}
