package com.anita.anitamotorcycle.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.anita.anitamotorcycle.beans.RecordBean;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.interfaces.IRepairingCallback;
import com.anita.anitamotorcycle.interfaces.IRepairingPresenter;
import com.anita.anitamotorcycle.utils.ClientUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Anita
 * @description:维修中，维修记录逻辑层(单例模式)
 * @date : 2020/2/5 22:08
 */
public class RepairingPresenter implements IRepairingPresenter {
    private List<IRepairingCallback> mCallbacks = new ArrayList<>();
    private List<RecordBean> mDatas = null;
    private static final String TAG = "RepairingPresenter";

    /**
     * 构造函数为 private,不会被实例化
     */
    private RepairingPresenter() {

    }

    private static RepairingPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static RepairingPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RepairingPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RepairingPresenter();
                }
            }
        }
        return sInstance;
    }


    @Override
    public void getRepairingList() {
//        发起请求
        updateLoading();

        if (MotorHelper.getInstance().getCurrentMotorId() != null) {
            Log.d(TAG, "getRepairingList: ");
            //        获取数据
//        创建数据集合
            mDatas = ClientUtils.getRepairingList(UserHelper.getInstance().getPhone(),0);
            Log.d(TAG, "getRepairingList: mDatas--" + mDatas);

        }

//        数据回来，更新UI
        handlerMyMotorResult(mDatas);

    }


    /**
     * 通知UI更新
     *
     * @param datas
     */
    private void handlerMyMotorResult(List<RecordBean> datas) {
        if (datas != null) {
            Log.d(TAG, "handlerMyMotorResult: ");
            //测试，清空一下，让界面显示空
            //albumList.clear();
            if (datas.size() == 0) {
                for (IRepairingCallback callback : mCallbacks) {
                    callback.onEmpty(); //数据为空
                }
            } else {
//                遍历集合中的每一个回调
                for (IRepairingCallback callback : mCallbacks) {
                    callback.onRepairingListLoaded(datas);
                }

            }
        } else {
            Log.d(TAG, "handlerMyMotorResult: data==null");
            for (IRepairingCallback callback : mCallbacks) {
                callback.onEmpty(); //数据为空
            }

        }

    }

    private void updateLoading() {
        for (IRepairingCallback callback : mCallbacks) {
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
    public void registerViewCallback(IRepairingCallback iRepairingCallback) {
        if (mCallbacks != null && !mCallbacks.contains(iRepairingCallback)) {
            mCallbacks.add(iRepairingCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRepairingCallback iRepairingCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iRepairingCallback);
        }
    }


}
