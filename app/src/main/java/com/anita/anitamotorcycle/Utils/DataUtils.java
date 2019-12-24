package com.anita.anitamotorcycle.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author weizhen
 * @description:
 * @date : 2019/11/1 8:21
 */
public class DataUtils {
    private static final String TAG = "DataUtils";

    /**
     * 将头像保存到sd卡中
     *
     * @param mBitmap
     */
    public static void savaPicToSDCard(Bitmap mBitmap) {
        Log.d(TAG, "savaPicToSDCard() 将头像保存到sd卡中");
        File file = new File(Constants.SD_PATH);
        file.mkdirs();
        Log.d(TAG, "savaPicToSDCard: file.exists()==" + file.exists());
        String headpath = Constants.SD_PATH + "head.jpg";
        Log.d(TAG, "savaPicToSDCard: file== " + file);
        Log.d(TAG, "savaPicToSDCard: headpath==" + headpath);
        try {
            FileOutputStream fos = new FileOutputStream(headpath);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把数据写入文件（compress：压缩）
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     * @param activity
     */
    public static void cropPhoto(Uri uri, Activity activity) {
        Log.d(TAG, "裁剪图片.");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("return-data", true);

        activity.startActivityForResult(intent, 3);
    }
}
