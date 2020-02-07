package com.anita.anitamotorcycle.presenters;

import com.anita.anitamotorcycle.interfaces.IMyMotorPresenter;
import com.anita.anitamotorcycle.interfaces.IMyMotorViewCallback;

/**
 * @author Anita
 * @description:我的摩托车逻辑层(单例模式)
 * @date : 2020/2/5 22:08
 */
public class MyMotorPresenter implements IMyMotorPresenter {

    private static final String TAG = "MyMotorPresenter";

    /**
     * 构造函数为 private,不会被实例化
     */
    private MyMotorPresenter(){

    }

    private static MyMotorPresenter sInstance = null;

    /**
     * 获取单例对象
     * @return
     */
    public static MyMotorPresenter getInstance() {
        if(sInstance == null) {
            synchronized(MyMotorPresenter.class) {
                if(sInstance == null) {
                    sInstance = new MyMotorPresenter();
                }
            }
        }
        return sInstance;
    }


    @Override
    public void getMyMotorList() {
//        获取摩托车列表内容


    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IMyMotorViewCallback iMyMotorViewCallback) {

    }

    @Override
    public void unRegisterViewCallback(IMyMotorViewCallback iMyMotorViewCallback) {

    }
}
