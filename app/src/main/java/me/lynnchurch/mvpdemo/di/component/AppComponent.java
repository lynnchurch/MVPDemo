package me.lynnchurch.mvpdemo.di.component;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import me.lynnchurch.base.ActivityManager;
import me.lynnchurch.base.di.module.AppModule;
import me.lynnchurch.base.rxerrorhandler.core.RxErrorHandler;
import me.lynnchurch.base.widget.imageloader.ImageLoader;
import me.lynnchurch.mvpdemo.di.module.CacheModule;
import me.lynnchurch.base.di.module.ClientModule;
import me.lynnchurch.base.di.module.HttpConfigModule;
import me.lynnchurch.base.di.module.ImageModule;
import me.lynnchurch.mvpdemo.di.module.ServiceModule;
import me.lynnchurch.mvpdemo.mvp.model.api.cache.CacheManager;
import me.lynnchurch.mvpdemo.mvp.model.api.service.ServiceManager;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {AppModule.class, HttpConfigModule.class, ClientModule.class, ServiceModule.class, CacheModule.class, ImageModule.class})
public interface AppComponent {
    Application Application();

    Gson gson();

    ActivityManager activityManager();

    RxErrorHandler rxErrorHandler();

    ServiceManager serviceManager();

    CacheManager cacheManager();

    OkHttpClient okHttpClient();

    ImageLoader imageLoader();
}
