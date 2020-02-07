package com.anita.anitamotorcycle.interfaces;

import com.anita.anitamotorcycle.beans.MotorItem;

import java.util.List;

/**
 * @author Anita
 * @description:通知我的摩托车ui，界面主动发起的动作
 * @date : 2020/2/5 22:00
 */
public interface IMyMotorViewCallback {
    /**
     * 获取内容的结果
     *
     * @param result
     */
    void onMyMotorListLoaded(List<MotorItem> result);

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
