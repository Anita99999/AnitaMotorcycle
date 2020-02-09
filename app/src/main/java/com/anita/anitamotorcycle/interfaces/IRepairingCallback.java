package com.anita.anitamotorcycle.interfaces;

import com.anita.anitamotorcycle.beans.MotorItem;
import com.anita.anitamotorcycle.beans.RecordItem;

import java.util.List;

/**
 * @author Anita
 * @description:通知维修记录维修中ui，界面主动发起的动作
 * @date : 2020/2/5 22:00
 */
public interface IRepairingCallback {
    /**
     * 获取内容的结果
     *
     * @param result
     */
    void onRepairingListLoaded(List<RecordItem> result);

    /**
     * 网络错误
     */
    void onNetworkError();


    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void onLoading();
}
