package me.lynnchurch.base;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import me.lynnchurch.base.di.component.DaggerBaseComponent;
import me.lynnchurch.base.di.module.AppModule;
import me.lynnchurch.base.di.module.ClientModule;
import me.lynnchurch.base.di.module.HttpConfigModule;
import me.lynnchurch.base.di.module.ImageModule;

import static me.lynnchurch.base.rxerrorhandler.utils.Preconditions.checkNotNull;

public abstract class BaseApplication extends Application {
    static private BaseApplication mApplication;
    private ClientModule mClientModule;
    private AppModule mAppModule;
    private ImageModule mImagerModule;
    private HttpConfigModule mHttpConfigModule;

    @Inject
    protected ActivityManager mActivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mAppModule = new AppModule(this);
        DaggerBaseComponent
                .builder()
                .appModule(mAppModule)
                .build()
                .inject(this);
        mImagerModule = new ImageModule(); // 图片加载框架默认使用glide
        mClientModule = new ClientModule(mActivityManager); // 用于提供okhttp和retrofit的单例
        mHttpConfigModule = checkNotNull(getHttpConfigModule(), "HttpConfigModule is required");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mClientModule = null;
        mAppModule = null;
        mImagerModule = null;
        mActivityManager.release();
        mActivityManager = null;
        mApplication = null;
    }
    protected abstract HttpConfigModule getHttpConfigModule();

    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppModule getAppModule() {
        return mAppModule;
    }

    public ImageModule getImageModule() {
        return mImagerModule;
    }

    public ActivityManager getActivityManager() {
        return mActivityManager;
    }

    public static Context getContext() {
        return mApplication;
    }

}
