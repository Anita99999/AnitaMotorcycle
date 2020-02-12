package com.anita.anitamotorcycle.interfaces;

import com.anita.anitamotorcycle.beans.RecordBean;

import java.util.List;

/**
 * @author Anita
 * @description:通知维修记录维修完成ui，界面主动发起的动作
 * @date : 2020/2/5 22:00
 */
public interface IRepairedCallback {
    /**
     * 获取内容的结果
     *
     * @param result
     */
    void onRepairedListLoaded(List<RecordBean> result);

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
