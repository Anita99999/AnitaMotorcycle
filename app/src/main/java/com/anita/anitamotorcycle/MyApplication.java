package com.anita.anitamotorcycle;

import android.app.Application;
import android.os.Handler;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {
    private static Handler sHandler = null;

    public void onCreate(){
        super.onCreate();
//        初始化AndroidUtilCode
        Utils.init(this);

        sHandler = new Handler();
    }

    public static Handler getHandler() {
        return sHandler;
    }
}
