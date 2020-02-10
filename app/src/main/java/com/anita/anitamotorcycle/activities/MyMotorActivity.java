package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.MyMotorDataAdapter;
import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.helps.MotorHelper;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的摩托车
 */
public class MyMotorActivity extends BaseActivity {
    private static final String TAG = "MyMotorActivity";

    private MyMotorDataAdapter mDataAdapter;
    private RecyclerView mMyMototList;
    private List<MotorItem> mDatas;
    private ImageView mBack;
    private TextView mAddMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motor);
//        找到控件

        initView();
        initListener();
        getData();
        showList(); //实现list

    }

    /*
    TODO：从网络中获取数据，暂时模拟数据
     */
    private void getData() {
//        创建数据集合
        mDatas = new ArrayList<>();
//        创建模拟数据
        for (int i = 1; i <= 2; i++) {
//            创建数据对象
            MotorItem data = new MotorItem();
            data.setPlate_numbers("车牌号" + i);
            data.setModel("车辆型号" + i);
            data.setBrand("制造商" + i);
            data.setBuy_at("保修日期"+i);
            data.setWarranty_distance(10000 - i);
//            data.url = "https://www.honda-sundiro.com/UpImage/Relate/20191104170922.jpg";
//            添加到集合里
            mDatas.add(data);
        }
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

        if (MotorHelper.getInstance().getCurrentMotorId() == null){
//          未添加摩托车
            mMyMototList.setVisibility(View.INVISIBLE);
        }else{
            mMyMototList.setVisibility(View.VISIBLE);
        }
    }

    private void showList() {
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
        mDataAdapter = new MyMotorDataAdapter(mDatas);
//        设置adaptor到recyclerview里
        mMyMototList.setAdapter(mDataAdapter);
    }

}
