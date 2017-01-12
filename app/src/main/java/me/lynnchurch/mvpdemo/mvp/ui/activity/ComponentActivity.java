package me.lynnchurch.mvpdemo.mvp.ui.activity;

import me.lynnchurch.base.BaseActivity;
import me.lynnchurch.base.mvp.BasePresenter;
import me.lynnchurch.mvpdemo.app.DemoApplication;
import me.lynnchurch.mvpdemo.di.component.AppComponent;

public abstract class ComponentActivity<P extends BasePresenter> extends BaseActivity<P> {
    protected DemoApplication mDemoApplication;
    @Override
    protected void setApplication() {
        mDemoApplication = (DemoApplication) getApplication();
        componentInject(mDemoApplication.getAppComponent());
    }

    protected abstract void componentInject(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mDemoApplication = null;
    }
}
