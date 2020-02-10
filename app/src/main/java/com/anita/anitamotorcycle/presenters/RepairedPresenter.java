package com.anita.anitamotorcycle.presenters;

import android.util.Log;

import com.anita.anitamotorcycle.beans.RecordItem;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.interfaces.IRepairedCallback;
import com.anita.anitamotorcycle.interfaces.IRepairedPresenter;
import com.anita.anitamotorcycle.interfaces.IRepairingCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Anita
 * @description:维修完成，维修记录逻辑层(单例模式)
 * @date : 2020/2/9 11:28
 */
public class RepairedPresenter implements IRepairedPresenter {
    private List<IRepairedCallback> mCallbacks = new ArrayList<>();
    private List<RecordItem> mDatas;
    private List<RecordItem> mCurrentDatas = null;
    private static final String TAG = "RepairedPresenter";

    /**
     * 构造函数为 private,不会被实例化
     */
    private RepairedPresenter() {

    }

    private static RepairedPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static RepairedPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RepairedPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RepairedPresenter();
                }
            }
        }
        return sInstance;
    }


    //    @Override
    public void getRepairedList() {
        //        发起请求

        updateLoading();

        if (MotorHelper.getInstance().getCurrentMotorId() != null) {
//        获取数据
//        创建数据集合
            mDatas = new ArrayList<>();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            String dateFormat = sdf.format(date);// new Date()为获取当前系统时间
//        创建模拟数据
            for (int i = 1; i <= 0; i++) {
//            创建数据对象
                RecordItem data = new RecordItem();
                data.setRepair_status("提交成功" + i);
                data.setPlate_numbers("维修完成车牌号" + i);
                data.setUpdate_at(dateFormat);
                data.setFactory_name("商家名" + i);
                data.setProblem_type("故障类型" + i);
//            添加到集合里
                mDatas.add(data);
            }

        }

//        数据回来，更新UI
        handlerMyMotorResult(mDatas);
    }

    /**
     * 通知UI更新
     *
     * @param datas
     */
    private void handlerMyMotorResult(List<RecordItem> datas) {
        if (datas != null) {
            Log.d(TAG, "handlerMyMotorResult: ");
            //测试，清空一下，让界面显示空
            //albumList.clear();
            if (datas.size() == 0) {
                for (IRepairedCallback callback : mCallbacks) {
                    callback.onEmpty(); //数据为空
                }
            } else {
//                遍历集合中的每一个回调
                for (IRepairedCallback callback : mCallbacks) {
                    callback.onRepairedListLoaded(datas);
                }
                this.mCurrentDatas = datas;
            }
        }else{
            Log.d(TAG, "handlerMyMotorResult: data==null");
            for (IRepairedCallback callback : mCallbacks) {
                callback.onEmpty(); //数据为空
            }
        }
    }

    private void updateLoading() {
        for (IRepairedCallback callback : mCallbacks) {
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
    public void registerViewCallback(IRepairedCallback iRepairedCallback) {
        if (mCallbacks != null && !mCallbacks.contains(iRepairedCallback)) {
            mCallbacks.add(iRepairedCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRepairedCallback iRepairedCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iRepairedCallback);
        }
    }
}
