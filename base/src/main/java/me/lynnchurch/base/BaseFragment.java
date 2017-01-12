package me.lynnchurch.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import me.lynnchurch.base.mvp.BasePresenter;

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment {
    protected BaseActivity mActivity;
    protected View mRootView;
    @Inject
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        ComponentInject();
        initData();
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        this.mActivity = null;
        this.mRootView = null;
    }

    protected abstract View initView();

    protected abstract void initData();

}
