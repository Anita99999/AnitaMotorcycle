package com.anita.anitamotorcycle.presenters;

import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.interfaces.IMyMotorPresenter;
import com.anita.anitamotorcycle.interfaces.IMyMotorViewCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anita
 * @description:我的摩托车逻辑层(单例模式)
 * @date : 2020/2/5 22:08
 */
public class MyMotorPresenter implements IMyMotorPresenter {
    private List<IMyMotorViewCallback> mCallbacks = new ArrayList<>();
    private List<MotorItem> mDatas;
    private List<MotorItem> mCurrentDatas = null;
    private static final String TAG = "MyMotorPresenter";

    /**
     * 构造函数为 private,不会被实例化
     */
    private MyMotorPresenter() {

    }

    private static MyMotorPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static MyMotorPresenter getInstance() {
        if (sInstance == null) {
            synchronized (MyMotorPresenter.class) {
                if (sInstance == null) {
                    sInstance = new MyMotorPresenter();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取摩托车列表内容
     * 1. TODO：从网络中获取数据，暂时模拟数据
     * 2. 数据回来后，更新UI
     */
    @Override
    public void getMyMotorList() {
//        发起请求

        updateLoading();
//        获取数据
//        创建数据集合
        mDatas = new ArrayList<>();
//        创建模拟数据
        for (int i = 1; i <= 0; i++) {
//            创建数据对象
            MotorItem data = new MotorItem();
            data.setPlate_numbers("车牌号" + i);
            data.setModel("车辆型号" + i);
            data.setBrand("制造商" + i);
            data.setBuy_at("" + i);
            data.setWarranty_distance(10000 - i);
//            data.url = "https://www.honda-sundiro.com/UpImage/Relate/20191104170922.jpg";
//            添加到集合里
            mDatas.add(data);
        }

//        数据回来，更新UI
        handlerMyMotorResult(mDatas);

    }

    /**
     * 通知UI更新
     * @param datas
     */
    private void handlerMyMotorResult(List<MotorItem> datas) {
        if(datas != null) {
            //测试，清空一下，让界面显示空
            //albumList.clear();
            if(datas.size() == 0) {
                for(IMyMotorViewCallback callback : mCallbacks) {
                    callback.onEmpty(); //数据为空
                }
            } else {
//                遍历集合中的每一个回调
                for(IMyMotorViewCallback callback : mCallbacks) {
                    callback.onMyMotorListLoaded(datas);
                }
                this.mCurrentDatas = datas;
            }
        }
    }

    private void updateLoading() {
        for(IMyMotorViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }


    @Override
    public void registerViewCallback(IMyMotorViewCallback callback) {
        if(mCallbacks != null && !mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IMyMotorViewCallback callback) {
        if(mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }


}
