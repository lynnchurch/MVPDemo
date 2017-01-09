package me.lynnchurch.base.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<M extends IModel, V extends BaseView> implements Presenter {
    protected CompositeSubscription mCompositeSubscription;

    protected M mModel;
    protected V mRootView;

    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {
    }

    @Override
    public void onDestroy() {
        unSubscribe(); // 解除订阅
        if (mModel != null) {
            mModel.onDestory();
            mModel = null;
        }
        mRootView = null;
        mCompositeSubscription = null;
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
