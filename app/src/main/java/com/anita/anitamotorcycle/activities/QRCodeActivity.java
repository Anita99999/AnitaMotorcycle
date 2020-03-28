package com.anita.anitamotorcycle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.utils.Constants;
import com.anita.anitamotorcycle.utils.QRCodeUtils;
import com.bumptech.glide.util.LogTime;

import java.io.File;

public class QRCodeActivity extends AppCompatActivity {

    private static final String TAG = "QRCodeActivity";

    private String currentMotorId;
    private ImageView mIv_back;
    private ImageView mIv_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initView();
        initListener();
    }

    private void initListener() {
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initView() {
//        获取上一页面传递的
        currentMotorId = getIntent().getStringExtra("currentMotorId");

        mIv_back = findViewById(R.id.iv_back);
        mIv_qrcode = findViewById(R.id.iv_qrcode);
        //判断URL合法性
        if (currentMotorId == null || "".equals(currentMotorId) || currentMotorId.length() < 1) {
            Log.d(TAG, "生成二维码内容不合法");
            return;
        }
        //获取logo资源,
        //R.drawable.logo为logo图片
//        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        File sdpath = new File(Constants.SD_PATH);
        Bitmap codeBitmap;
        if (sdpath.exists()) {
            Bitmap bt = BitmapFactory.decodeFile(Constants.SD_PATH + "head.jpg");//从Sd中找头像，转换成Bitmap
            if (bt != null) {
                codeBitmap = QRCodeUtils.createQRCode(currentMotorId, 500, 500, bt);

                Log.d(TAG, "initView: 本地有头像图片");
            } else {
                //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略
                codeBitmap = QRCodeUtils.createQRCode(currentMotorId, 500, 500, null);
                Log.d(TAG, "initView: 本地无头像图片");
            }
            //生成二维码
            mIv_qrcode.setImageBitmap(codeBitmap);//显示二维码
        } else {
            Log.d(TAG, "initView: sdpath不存在");
        }

    }

}
