package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.hb.dialog.dialog.ConfirmDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddMotorNumActivity extends AppCompatActivity {
    private static final String TAG = "AddMotorNumActivity";

    private ImageView mIv_back;
    private TextView mTv_ok;
    private EditText mEt_plate_numers;
    private String mPlateNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motor_num);

        initView();
        initListener();
    }

    private void initListener() {
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

        /**
         * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间
         * 2. 检验车牌号不在当前数据库
         */
        mTv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEt_plate_numers.getText().toString())) {
                    Toast.makeText(AddMotorNumActivity.this, "车牌号不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                清空数据
                MotorHelper.getInstance().setMotorBean(null);
//                获取车牌号
                mPlateNumbers = mEt_plate_numers.getText().toString();
                MotorHelper.getInstance().getMotorBean().setPlate_numbers(mPlateNumbers);
//                获取用户phone
                MotorHelper.getInstance().getMotorBean().setUser_id(UserHelper.getInstance().getPhone());
//                获取当前北京时间,设置创建时间和更新时间
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                String currentDate = sdf.format(date);// new Date()为获取当前系统时间
                MotorHelper.getInstance().getMotorBean().setCreate_at(currentDate);
                MotorHelper.getInstance().getMotorBean().setUpdate_at(currentDate);

//                连接数据库，验证是否存在该车辆
                Thread validateNumbersThread = new Thread(new ValidateNumbersThread());
                validateNumbersThread.start();

            }


        });
    }

    private void initView() {
        mIv_back = findViewById(R.id.iv_back);
        mTv_ok = findViewById(R.id.tv_ok);

        mEt_plate_numers = findViewById(R.id.et_plate_numbers);

    }

    /**
     * 连接数据库
     * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间(motoritem)
     * 2. 检验车牌号不在当前数据库
     */
    class ValidateNumbersThread implements Runnable {

        @Override
        public void run() {
            boolean rs = ClientUtils.validateNumbers(AddMotorNumActivity.this, MotorHelper.getInstance().getMotorBean());
            Log.d(TAG, "validateNumbers结果==" + rs);
            if (rs) {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "当前车辆" + mPlateNumbers + "已被添加！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                showAlertDialog();  //弹窗确认车牌号
            }

        }
    }

    /**
     * 弹窗确认车牌号
     */
    private void showAlertDialog() {
        Looper.prepare();
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认添加车辆" + mPlateNumbers + "吗？");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
//                连接数据库，update摩托车数据
                Thread addMotorThread = new Thread(new AddMotorThread());
                addMotorThread.start();

            }

            @Override
            public void cancel() {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
        Looper.loop();

    }

    /**
     * 连接数据库
     * 1. 传回当前用户phone、摩托车基本信息、车牌号、创建更新时间(motoritem)
     * 2. update摩托车数据
     */
    class AddMotorThread implements Runnable {

        @Override
        public void run() {
            boolean rs = ClientUtils.addMotor(AddMotorNumActivity.this, MotorHelper.getInstance().getMotorBean());
            if (rs) {
                Log.d(TAG, "添加摩托车 ");

                if (MotorHelper.getInstance().getCurrentMotorId() == null) {
//                无标记,利用SharedPreferences保存摩托车标记
                    boolean isSave = MotorUtils.saveMotor(AddMotorNumActivity.this, MotorHelper.getInstance().getMotorBean().getId());
                    if (isSave) {
//                      保存摩托车标记，在全局单例类MotorHelp之中
                        MotorHelper.getInstance().setCurrentMotorId(MotorHelper.getInstance().getMotorBean().getId());
                        successAdd();
                    } else {
                        Looper.prepare();
                        Toast.makeText(AddMotorNumActivity.this, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } else {
                    Log.d(TAG, "run: 已有摩托车标记");
                    successAdd();
                }


            } else {
                Log.d(TAG, "添加失败");
            }

        }
    }


    private void successAdd() {
        Looper.prepare();
        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(AddMotorNumActivity.this, MainActivity.class);
//                      添加intent标志符：清除当前TASK栈占用的Activity、创建一个新的TASK栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        MotorHelper.getInstance().setMotorBean(null);
        Looper.loop();
    }



}
