package com.anita.anitamotorcycle.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.MotorUtils;

public class AddMotorActivity extends BaseActivity {
    private static final String TAG = "AddMotorActivity";

    private ImageView mBack;
    private TextView mNext;
    private ImageView mInfo;
    private EditText mVinCode;
    private String mVin;
    private MotorItem mMotorItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motor);

        initView();
        initListener();
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

        /**
         * 下一步点击事件：
         * 1. 验证vin是否输入合法
         * 2. 连接数据库，验证是否存在该车辆（若存在，返回摩托车数据，更新ui）
         */
        mNext.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                mVin = mVinCode.getText().toString();

//                验证vin输入合法性
                boolean vinValidate = MotorUtils.VINValidate(mVin);
                Log.d(TAG, "onClick:VINValidate-- " + vinValidate);
                if (!vinValidate) {
                    Toast.makeText(AddMotorActivity.this, "车架号输入错误，请检查！", Toast.LENGTH_SHORT).show();
                    return;
                }


//                连接数据库，验证是否存在该车辆（若存在，返回摩托车数据，更新ui）
                Thread validateVINThread = new Thread(new validateVINThread());
                validateVINThread.start();

            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mNext = findViewById(R.id.tv_next);

        mVinCode = findViewById(R.id.et_vin_code);
        mInfo = findViewById(R.id.iv_info);

    }

    /**
     * 连接数据库，验证是否存在该车辆（若存在，返回摩托车数据，更新ui）
     */
    class validateVINThread implements Runnable {

        @Override
        public void run() {
            mMotorItem = ClientUtils.validateVIN(mVin, AddMotorActivity.this);
            if (mMotorItem == null) {
                Log.d(TAG, "run: motorItem==null");
                Looper.prepare();
                Toast.makeText(AddMotorActivity.this, "服务器出错！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                Log.d(TAG, "onClick: motorItem--" + mMotorItem.toString());
//                            查询无车辆
                if (mMotorItem.getId() == null) {
                    Looper.prepare();
                    Toast.makeText(AddMotorActivity.this, "无该车辆！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else if (mMotorItem.getCreate_at() != null) {
//                            查询有车辆，但已被添加
                    Looper.prepare();
                    Toast.makeText(AddMotorActivity.this, "该车辆已添加", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
//                    vin正确,可添加
                    MotorHelper.getInstance().setMotorItem(mMotorItem);
                    Intent intent = new Intent(getApplicationContext(), AddMotorInfoActivity.class);
                    startActivity(intent);
                }
            }
        }
    }


    private void showAlertDialog() {
        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("车架号")//标题
                .setMessage("仅限添加本品牌（五羊本田）摩托车。")//内容
                .setIcon(R.mipmap.tips)//图标
                .create();
        alertDialog1.show();

    }
}
