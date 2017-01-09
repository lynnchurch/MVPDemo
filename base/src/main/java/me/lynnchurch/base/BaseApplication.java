package me.lynnchurch.base;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import me.lynnchurch.base.di.component.DaggerBaseComponent;
import me.lynnchurch.base.di.module.AppModule;
import me.lynnchurch.base.di.module.ClientModule;
import me.lynnchurch.base.di.module.GlobalConfigModule;
import me.lynnchurch.base.di.module.ImageModule;

import static me.lynnchurch.base.utils.Preconditions.checkNotNull;

public abstract class BaseApplication extends Application {
    static private BaseApplication mApplication;
    private ClientModule mClientModule;
    private AppModule mAppModule;
    private ImageModule mImagerModule;
    private GlobalConfigModule mGlobeConfigModule;
    @Inject
    protected ActivityManager mActivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mAppModule = new AppModule(this); // 提供Application
        DaggerBaseComponent
                .builder()
                .appModule(mAppModule)
                .build()
                .inject(this);
        this.mImagerModule = new ImageModule(); // 图片加载框架默认使用glide
        this.mClientModule = new ClientModule(mActivityManager); // 用于提供okhttp和retrofit的单例
        this.mGlobeConfigModule = checkNotNull(getGlobeConfigModule(), "globalConfigModule is required");

    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mClientModule != null) {
            this.mClientModule = null;
        }
        if (mAppModule != null) {
            this.mAppModule = null;
        }
        if (mImagerModule != null) {
            this.mImagerModule = null;
        }
        if (mActivityManager != null) {
            this.mActivityManager.release(); //释放资源
            this.mActivityManager = null;
        }
        if (mApplication != null) {
            this.mApplication = null;
        }
    }


    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     *
     * @return
     */
    protected abstract GlobalConfigModule getGlobeConfigModule();


    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppModule getAppModule() {
        return mAppModule;
    }

    public ImageModule getImageModule() {
        return mImagerModule;
    }


    public ActivityManager getAppManager() {
        return mActivityManager;
    }


    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

}
