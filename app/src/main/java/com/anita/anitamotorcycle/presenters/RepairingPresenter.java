package com.anita.anitamotorcycle.presenters;

import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.beans.RecordItem;
import com.anita.anitamotorcycle.interfaces.IMyMotorViewCallback;
import com.anita.anitamotorcycle.interfaces.IRepairingCallback;
import com.anita.anitamotorcycle.interfaces.IRepairingPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Anita
 * @description:维修中维修记录逻辑层(单例模式)
 * @date : 2020/2/5 22:08
 */
public class RepairingPresenter implements IRepairingPresenter {
    private List<IRepairingCallback> mCallbacks = new ArrayList<>();
    private List<RecordItem> mDatas;
    private List<RecordItem> mCurrentDatas = null;
    private static final String TAG = "MyMotorPresenter";

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
            data.setPlate_numbers("车牌号" + i);
            data.setUpdate_at(dateFormat);
            data.setFactory_name("商家名" + i);
            data.setProblem_type("故障类型" + i);
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
    private void handlerMyMotorResult(List<RecordItem> datas) {
        if(datas != null) {
            //测试，清空一下，让界面显示空
            //albumList.clear();
            if(datas.size() == 0) {
                for(IRepairingCallback callback : mCallbacks) {
                    callback.onEmpty(); //数据为空
                }
            } else {
//                遍历集合中的每一个回调
                for(IRepairingCallback callback : mCallbacks) {
                    callback.onRepairingListLoaded(datas);
                }
                this.mCurrentDatas = datas;
            }
        }
    }

    private void updateLoading() {
        for(IRepairingCallback callback : mCallbacks) {
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
        if(mCallbacks != null && !mCallbacks.contains(iRepairingCallback)) {
            mCallbacks.add(iRepairingCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRepairingCallback iRepairingCallback) {
        if(mCallbacks != null) {
            mCallbacks.remove(iRepairingCallback);
        }
    }
}
