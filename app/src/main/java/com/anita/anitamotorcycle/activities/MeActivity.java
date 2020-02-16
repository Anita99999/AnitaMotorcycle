package com.anita.anitamotorcycle.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.beans.UserBean;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.Constants;
import com.anita.anitamotorcycle.utils.DataUtils;
import com.anita.anitamotorcycle.utils.PermissionUtils;
import com.leon.lib.settingview.LSettingItem;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeActivity extends BaseActivity {
    private static final String TAG = "MeActivity";
    private LSettingItem mLs_change_password;
    private FrameLayout fl_head;
    private CircleImageView iv_headIcon;

    private Bitmap bitmap;
    private LSettingItem mLs_phone;
    private LSettingItem mLs_name;
    private LSettingItem mLs_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
        initListener();

    }

    private void initView() {
//        获取上一页面传递的数据
        UserBean userBean = (UserBean) getIntent().getSerializableExtra("user");
        Log.d(TAG, "initView: userbean--"+userBean.toString());
        initNavBar(true, "个人信息");
        fl_head = findViewById(R.id.fl_head);
        iv_headIcon = findViewById(R.id.iv_headIcon);
//        用户名
        mLs_name = findViewById(R.id.ls_name);
        mLs_name.setRightText(userBean.getName());
//        手机号
        mLs_phone = findViewById(R.id.ls_phone);
        mLs_phone.setRightText(userBean.getPhone());
//        性别
        mLs_sex = findViewById(R.id.ls_sex);
        mLs_sex.setRightText(userBean.getSex());

        mLs_change_password = findViewById(R.id.ls_change_password);

        bitmap = ((BitmapDrawable) iv_headIcon.getDrawable()).getBitmap();
        File sdpath = new File(Constants.SD_PATH);
        if (sdpath.exists()) {
            Bitmap bt = BitmapFactory.decodeFile(Constants.SD_PATH + "head.jpg");//从Sd中找头像，转换成Bitmap
            if (bt != null) {
                //如果本地有头像图片的话
                iv_headIcon.setImageBitmap(bt);
                Log.d(TAG, "initView: 本地有头像图片");
            } else {
                //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略
                Log.d(TAG, "initView: 本地无头像图片");
            }
        } else {
            Log.d(TAG, "initView: sdpath不存在");
        }
    }

    private void initListener() {
//        头像
        fl_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog(MeActivity.this);   //弹出设置头像图片的Dialog
            }
        });

//        修改密码
        mLs_change_password.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(MeActivity.this, ChangePasswordActivity.class));
            }
        });

    }


    /**
     * 弹出设置头像图片的Dialog
     *
     * @param activity
     */
    public static void showImagePickDialog(final Activity activity) {
        String title = "选择获取头像的方式";
        String[] items = new String[]{"拍照", "相册"};
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
//                                选择拍照
                                PermissionUtils.getCameraPermission(activity);
                                PermissionUtils.pickImageFromCamera(activity);
                                break;
//                                选择相册
                            case 1:
                                PermissionUtils.getSDCardReadPermission(activity);
                                PermissionUtils.pickImageFromAlbum(activity);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            调用相机后返回
            case 1:
                if (resultCode == RESULT_OK) {
                    bitmap = data.getParcelableExtra("data");
                    DataUtils.savaPicToSDCard(bitmap);//保存在SD卡中
                    iv_headIcon.setImageBitmap(bitmap);//将头像设置为相机图片
                }
                break;
//            调用相册后返回
            case 2:
                if (resultCode == RESULT_OK) {
                    DataUtils.cropPhoto(data.getData(), this);//裁剪图片
                }
                break;
//            调用系统裁剪后返回
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    if (bitmap != null) {
                        /**
                         * 上传服务器代码
                         */
                        DataUtils.savaPicToSDCard(bitmap);//保存在SD卡中
                        iv_headIcon.setImageBitmap(bitmap);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: requestCode==" + requestCode + " grantResults==" + grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult:相机权限Permission Granted ");
                    PermissionUtils.pickImageFromCamera(this);
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Permission Denied");
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult:sd卡写入权限Permission Granted ");
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Permission Denied");
                }
                break;
            default:
                break;
        }

    }

    //        点击返回键返回数据给上一个MeFragment
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();   //作为传递数据使用
        //需要返回的数据
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        intent.putExtra("data", bitmapByte); //返回的是intent的该数据
        //返回数据，返回参数2代表数据名“data”中的数据data,参数1resultCode，
        setResult(2, intent);
        //返回数据后结束当前活动
        finish();
    }

}


