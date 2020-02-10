package com.anita.anitamotorcycle.interfaces;

/**
 * @author Anita
 * @description:我的摩托车，逻辑层通知界面更新接口
 * @date : 2020/2/5 21:57
 */
public interface IRepairedPresenter extends IBasePresenter<IRepairedCallback>{

    /**
     * 获取维修完成内容
     */
    void getRepairedList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上接加载更多
     */
    void loadMore();
}
