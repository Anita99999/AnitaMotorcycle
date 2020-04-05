package com.anita.anitamotorcycle.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.MotorBean;
import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.Constants;
import com.anita.anitamotorcycle.utils.MotorUtils;
import com.anita.anitamotorcycle.utils.UserUtils;
import com.hb.dialog.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class RepairApplicationActivity extends BaseActivity {

    private ArrayAdapter<String> mMotorAdapter;
    private Spinner mSp_plate_numbers;
    private Spinner mSp_problems_type;    //维修类型下拉列表
    private ArrayAdapter<String> mProblemsAdapter;
    private TextView mCommit;
    private ImageView mBack;
    private ImageView mTips2;
    private ImageView mTips1;
    private EditText mEt_username;
    private EditText mEt_phone;
    private String mPlateNumbers;
    private String mProblemsType;
    private EditText mEt_location;
    private EditText mEt_problems;
    private RecordBean mRecordBean;
    private TextView mTv_location;
    private boolean mIsRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_application);

        initView();
        initListener();
    }

    private void initView() {
        mBack = findViewById(R.id.iv_back);
        mCommit = findViewById(R.id.tv_commit);

        mEt_username = findViewById(R.id.et_username);
        mEt_phone = findViewById(R.id.et_phone);
        mEt_phone.setText(UserHelper.getInstance().getPhone());
//        车牌号提示
        mTips1 = findViewById(R.id.iv_tips1);
//        车牌号下拉列表内容
        mSp_plate_numbers = findViewById(R.id.sp_plate_numbers);
        List<String> motors_list = new ArrayList<>();
        List<MotorBean> MotorsData = MotorUtils.getMotorsData(this);
        if (MotorsData != null) {
            motors_list.add("请选择车辆");
            for (MotorBean m : MotorsData) {
                motors_list.add(m.getPlate_numbers());
            }
        } else {
            motors_list.add("请先添加车辆");
        }
        mMotorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motors_list);   //创建数组适配器
        mMotorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //设置下拉列表下拉时的菜单样式
        mSp_plate_numbers.setAdapter(mMotorAdapter);    //将适配器添加到下拉列表上

//        车辆位置
        mTips2 = findViewById(R.id.iv_tips2);
        mEt_location = findViewById(R.id.et_location);
        if (MotorHelper.getInstance().getLocation() != null) {
            mEt_location.setText(MotorHelper.getInstance().getLocation());
            Log.d(TAG, "initView: mEt_location重新获取定位");
        }
        mTv_location = findViewById(R.id.tv_location);

//        定义故障类型下拉列表内容
        mSp_problems_type = findViewById(R.id.sp_problems_type);
        List<String> problems_list = new ArrayList<String>();
        problems_list.add("请选择故障类型");
        problems_list.add("车轮爆胎");
        problems_list.add("制动失灵");
        problems_list.add("链条断裂");
        problems_list.add("发动机式倒挡器漏油");
        problems_list.add("油门把手损坏");
        problems_list.add("前轮轴承异响有损");
        problems_list.add("离合器异响及空转");
        problems_list.add("其他");
        mProblemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, problems_list);   //创建数组适配器
        mProblemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //设置下拉列表下拉时的菜单样式
        mSp_problems_type.setAdapter(mProblemsAdapter);    //将适配器添加到下拉列表上

        mEt_problems = findViewById(R.id.et_problems);

    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

//        车牌号下拉列表
        mSp_plate_numbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "车牌号选择: " + mMotorAdapter.getItem(position));
                mPlateNumbers = mMotorAdapter.getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: none");
            }
        });

//        车牌号提示
        mTips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(1);
            }
        });

//        车辆位置提示
        mTips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(2);
            }
        });

        mTv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RepairApplicationActivity.this, GetLocationActivity.class);
//                调用activity的onActivityResult()方法，返回存放返回数据的Intent和requestCode
                startActivityForResult(intent, 1000);
            }
        });
//        维修时间
//        mRepairTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog(2);
//            }
//        });

//        故障类型下拉列表
        mSp_problems_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "故障类型选择: " + mProblemsAdapter.getItem(position));
                mProblemsType = mProblemsAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: none");
            }
        });

//        提交按钮
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                检查字段
                if (!checkData()) {
                    return;
                }
//                if(mIsRepair){
//                    Toast.makeText(getApplicationContext(), "车牌号为" + mPlateNumbers + "的车辆正在维修中，请重新选择", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                确认提交弹框
                showConfirmDialog();
            }
        });


    }

    /**
     * 检查信息
     * 信息数据不正确，返回false；正确返回true
     *
     * @return
     */
    private boolean checkData() {
        String user_name = mEt_username.getText().toString();
        if (TextUtils.isEmpty(user_name)) {
            Toast.makeText(getApplicationContext(), "申请人姓名不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String phone = mEt_phone.getText().toString();
        boolean result = UserUtils.validatePhone(this, phone);    //验证手机号
        if (!result) {
            return false;
        }

        Log.d(TAG, "checkData: mPlateNumbers---" + mPlateNumbers);
        if (mPlateNumbers == "请选择车辆" || mPlateNumbers == "请先添加车辆") {
            Toast.makeText(getApplicationContext(), "车牌号不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        String position = mEt_location.getText().toString();
        if (TextUtils.isEmpty(position)) {
            Toast.makeText(getApplicationContext(), "车辆位置不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mProblemsType == "请选择故障类型") {
            Toast.makeText(getApplicationContext(), "故障类型不可为空", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            //            连接数据库，验证当前车牌号未处于维修状态
            Log.d(TAG, "checkData: ");
            mIsRepair = ClientUtils.validatePlateNumbers(mPlateNumbers);
            Log.d(TAG, "checkData: isRepair--" + mIsRepair);
            if (mIsRepair) {
//                车辆正在维修
                Toast.makeText(getApplicationContext(), "车辆" + mPlateNumbers + "正在维修中，请重新选择", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        String description = mEt_problems.getText().toString();
//        通过验证,保存数据
        mRecordBean = new RecordBean(user_name, phone, mPlateNumbers, position, mProblemsType, description);
        return true;
    }

    /**
     * 弹窗确认提交数据
     */
    private void showConfirmDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确认提交维修申请吗？");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
//                连接数据库，提交维修数据
                RecordBean record = ClientUtils.addRecord(mRecordBean);
                if (record == null) {
                    Toast.makeText(getApplicationContext(), "服务器连接超时，请检查", Toast.LENGTH_SHORT).show();
                } else if (record.getId() == null) {
                    Log.d(TAG, "提交失败");
                } else {
                    Log.d(TAG, "提交成功 ");
                    Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), RepairDetailsActivity.class);
                    intent.putExtra("record", record);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void cancel() {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_LONG).show();
            }
        });
        confirmDialog.show();
    }

    private void showAlertDialog(int i) {
        String a = "车牌号";
        String b = "若无所需车牌号，请先到我的摩托车中添加车辆。";
        if (i == 2) {
            a = "车辆位置";
            b = "维修员将根据此位置到点维修车辆，请确保车辆位置准确。";
        }
        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle(a)//标题
                .setMessage(b)//内容
                .setIcon(R.mipmap.tips)//图标
                .create();
        alertDialog1.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String location = data.getStringExtra("location");
            Log.d(TAG, "onActivityResult: 获取上页数据");
            mEt_location.setText(location);
        }

    }

//    private void showDatePickerDialog(int themeResId) {
//        Calendar calendar = Calendar.getInstance(Locale.CHINA);
//
//        DatePickerDialog dialog = new DatePickerDialog(this, themeResId, new DatePickerDialog.OnDateSetListener() {
//            // 绑定监听器(How the parent is notified that the date is set.)
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                // 得到选择的时间，可以进行你想要的操作
//                mRepairTime.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        dialog.setTitle("日期");
//        long day = 24 * 60 * 60 * 1000;
//
//        int hour;
//        if (calendar.get(Calendar.AM_PM) == 0) {
//            calendar.get(Calendar.HOUR);
//            hour = calendar.get(Calendar.HOUR);
//            Log.d(TAG, "showDatePickerDialog: hour==" + hour);
//        } else {
//            hour = calendar.get(Calendar.HOUR) + 12;
//            Log.d(TAG, "showDatePickerDialog: hour==" + hour);
//        }
//        if (hour < 17) {
//            dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() + day * 5);  //设置日期最大值
//            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis()); //设置日期最小值
//        } else {
//            dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() + day * 6);  //设置日期最大值
//            dialog.getDatePicker().setMinDate(calendar.getTimeInMillis() + day * 1); //设置日期最小值
//        }
//        dialog.show();
//    }

}
