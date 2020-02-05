package com.anita.anitamotorcycle.interfaces;

/**
 * @author Anita
 * @description:
 * @date : 2020/2/5 21:44
 */
public interface IBasePresenter<T> {
    /**
     * 注册UI的回调接口
     *
     * @param t
     */
    void registerViewCallback(T t);

    /**
     * 取消注册
     *
     * @param t
     */
    void unRegisterViewCallback(T t);
}
