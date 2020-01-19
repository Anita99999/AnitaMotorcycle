package com.anita.anitamotorcycle.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class RepairApplicationActivity extends BaseActivity {

    private ArrayAdapter<String> mMotorAdapter;
    private Spinner mMotorType;
    private Spinner mProblemsType;    //维修类型下拉列表
    private ArrayAdapter<String> mProblemsAdapter;
    private TextView mCommit;
    private ImageView mBack;

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

//        mRepairTime = findViewById(R.id.btn_time);
//        定义车牌号下拉列表内容
        mMotorType = findViewById(R.id.sp_motors_type);
        List<String> motors_list = new ArrayList<String>();
        motors_list.add("请先绑定车辆");

        mMotorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, motors_list);   //创建数组适配器
        mMotorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //设置下拉列表下拉时的菜单样式
        mMotorType.setAdapter(mMotorAdapter);    //将适配器添加到下拉列表上

//        定义故障类型下拉列表内容
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
        mProblemsType = findViewById(R.id.sp_problems_type);
        mProblemsType.setAdapter(mProblemsAdapter);    //将适配器添加到下拉列表上

    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //后退操作
            }
        });

//        车牌号下拉列表
        mMotorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "车牌号选择: " + mMotorAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: none");
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
        mProblemsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "故障类型选择: " + mProblemsAdapter.getItem(position));
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
                Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(), RepairRecordActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
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
