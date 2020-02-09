package com.anita.anitamotorcycle.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.anita.anitamotorcycle.MyApplication;
import com.anita.anitamotorcycle.R;


/**
 * @author Anita
 * @description:UI加载器
 * @date : 2020/2/8 13:03
 */
public abstract class UILoaderView extends FrameLayout {
    private View mLoadingView;
    private View mSuccessView;
    private View mNetworkErrorView;
    private View mEmptyView;

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoaderView(Context context) {
        this(context, null);
    }

    public UILoaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void updateStatus(UIStatus status) {
        mCurrentStatus = status;
        //更新UI一定要在主线程上。
        MyApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    /**
     * 初始化UI
     */
    private void init() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
//        加载中
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        //根据状态设置是否可见
        mLoadingView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);

//        成功
        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);
        }
        //根据状态设置是否可见
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);


//        网路错误页面
        if (mNetworkErrorView == null) {
            mNetworkErrorView = getNetworkErrorView();
            addView(mNetworkErrorView);
        }
        //根据状态设置是否可见
        mNetworkErrorView.setVisibility(mCurrentStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);


//        数据为空的界面
        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        //根据状态设置是否可见
        mEmptyView.setVisibility(mCurrentStatus == UIStatus.EMPTY ? VISIBLE : GONE);

    }

    protected View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    protected View getNetworkErrorView() {
        View networkErrorView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view, this, false);
//        networkErrorView.findViewById(R.id.network_error_icon).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //重新获取数据
//                if (mOnRetryClickListener != null) {
//                    mOnRetryClickListener.onRetryClick();
//                }
//            }
//        });

        return networkErrorView;
    }

    protected abstract View getSuccessView(ViewGroup container);

    protected View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }
}
