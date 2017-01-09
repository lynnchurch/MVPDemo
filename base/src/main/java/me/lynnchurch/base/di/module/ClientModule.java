package me.lynnchurch.base.di.module;

import android.app.Application;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.lynnchurch.base.ActivityManager;
import me.lynnchurch.base.http.RequestIntercept;
import me.lynnchurch.base.rxerrorhandler.core.RxErrorHandler;
import me.lynnchurch.base.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ClientModule {
    private static final int TIME_OUT = 10;
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024; // 缓存文件最大值为10MB
    private ActivityManager mActivityManager;


    public ClientModule(ActivityManager activityManager) {
        this.mActivityManager = activityManager;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
        return builder
                .baseUrl(httpUrl) // 域名
                .client(client) // 设置okhttp
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用rxjava
                .addConverterFactory(GsonConverterFactory.create()) // 使用Gson
                .build();
    }

    /**
     * 提供OkhttpClient
     *
     * @param okHttpClient
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder okHttpClient, Cache cache, Interceptor intercept
            , List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = okHttpClient
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cache(cache) // 设置缓存
                .addNetworkInterceptor(intercept);
        if (interceptors != null && interceptors.size() > 0) { // 如果外部提供了interceptor的数组则遍历添加
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder
                .build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    Cache provideCache(File cacheFile) {
        return new Cache(cacheFile, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE); // 设置缓存路径和大小
    }

    @Singleton
    @Provides
    Interceptor provideIntercept(RequestIntercept intercept) {
        return intercept; // 打印请求信息的拦截器
    }

    /**
     * 提供RXCache客户端
     *
     * @param cacheDir 缓存路径
     * @return
     */
    @Singleton
    @Provides
    RxCache provideRxCache(File cacheDir) {
        return new RxCache
                .Builder()
                .persistence(cacheDir, new GsonSpeaker());
    }

    /**
     * 提供处理Rxjava错误的管理器
     *
     * @return
     */
    @Singleton
    @Provides
    RxErrorHandler provideRxErrorHandler(Application application, ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErroListener(listener)
                .build();
    }

    /**
     * 提供管理所有activity的管理类
     *
     * @return
     */
    @Singleton
    @Provides
    ActivityManager provideActivityManager() {
        return mActivityManager;
    }
}
