package me.lynnchurch.base.mvp;

public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    protected M mModel;
    protected V mView;

    public BasePresenter(M model, V view) {
        this.mModel = model;
        this.mView = view;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }


    public void onStart() {
    }

    public void onDestroy() {
        if (null != mModel) {
            mModel.onDestory();
            mModel = null;
        }
        if (null != mView) {
            mView.onDestroy();
            mView = null;
        }
    }
}
