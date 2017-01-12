package me.lynnchurch.mvpdemo.mvp.presenter;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.lynnchurch.base.ActivityManager;
import me.lynnchurch.base.DefaultAdapter;
import me.lynnchurch.base.di.scope.ActivityScope;
import me.lynnchurch.base.mvp.BasePresenter;
import me.lynnchurch.base.rxerrorhandler.core.RxErrorHandler;
import me.lynnchurch.base.rxerrorhandler.handler.ErrorHandlerSubscriber;
import me.lynnchurch.base.rxerrorhandler.handler.RetryWithDelay;
import me.lynnchurch.base.utils.RxUtils;
import me.lynnchurch.mvpdemo.mvp.contract.UserContract;
import me.lynnchurch.mvpdemo.mvp.model.bean.User;
import me.lynnchurch.mvpdemo.mvp.ui.adapter.UserAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    private RxErrorHandler mErrorHandler;
    private ActivityManager mActivityManager;
    private Application mApplication;
    private List<User> mUsers = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int lastUserId = 1;


    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View view, RxErrorHandler handler
            , ActivityManager activityManager, Application application) {
        super(model, view);
        mApplication = application;
        mErrorHandler = handler;
        mActivityManager = activityManager;
        mAdapter = new UserAdapter(mUsers);
        mView.setAdapter(mAdapter);
    }

    public void requestUsers(final boolean pullToRefresh) {
        if (pullToRefresh) lastUserId = 1;
        mModel.getUsers(lastUserId, pullToRefresh)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2)) // 遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh) {
                            mView.showLoading(); // 显示下拉刷新的进度条
                        } else {
                            mView.startLoadMore();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh)
                            mView.hideLoading();// 隐藏下拉刷新的进度条
                        else
                            mView.stopLoadMore();
                    }
                })
                .compose(RxUtils.<List<User>>bindToLifecycle(mView)) // 使subscription和activity一起销毁
                .subscribe(new ErrorHandlerSubscriber<List<User>>(mErrorHandler) {
                    @Override
                    public void onNext(List<User> users) {
                        lastUserId = users.get(users.size() - 1).getId(); // 记录最后一个id,用于下一次请求
                        if (pullToRefresh) {
                            mUsers.clear(); // 如果是下拉刷新则清空列表
                        }
                        for (User user : users) {
                            mUsers.add(user);
                        }
                        mAdapter.notifyDataSetChanged(); // 通知更新数据
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mUsers = null;
        mErrorHandler = null;
        mActivityManager = null;
        mApplication = null;
    }
}
