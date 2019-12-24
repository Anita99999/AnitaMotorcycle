package com.anita.anitamotorcycle;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {

    public void onCreate(){
        super.onCreate();
//        初始化AndroidUtilCode
        Utils.init(this);
    }
}
