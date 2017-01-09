package me.lynnchurch.base.di.module;

import android.app.Application;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.lynnchurch.base.http.GlobalHttpHandler;
import me.lynnchurch.base.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.lynnchurch.base.utils.ConfigHelper;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

@Module
public class GlobalConfigModule {
    private HttpUrl mApiUrl;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private File mCacheFile;

    private GlobalConfigModule(Buidler buidler) {
        this.mApiUrl = buidler.apiUrl;
        this.mHandler = buidler.handler;
        this.mInterceptors = buidler.interceptors;
        this.mErrorListener = buidler.responseErrorListener;
        this.mCacheFile = buidler.cacheFile;
    }

    public static Buidler buidler() {
        return new Buidler();
    }

    @Singleton
    @Provides
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return mApiUrl;
    }

    @Singleton
    @Provides
    GlobalHttpHandler provideGlobeHttpHandler() {
        return mHandler == null ? GlobalHttpHandler.EMPTY : mHandler; // 打印请求信息
    }

    /**
     * 提供缓存地址
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return mCacheFile == null ? ConfigHelper.getCacheDir(application) : mCacheFile;
    }

    /**
     * 提供处理Rxjava错误的管理器的回调
     *
     * @return
     */
    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    public static final class Buidler {
        private HttpUrl apiUrl = HttpUrl.parse("https://api.github.com/");
        private GlobalHttpHandler handler;
        private List<Interceptor> interceptors = new ArrayList<>();
        private ResponseErrorListener responseErrorListener;
        private File cacheFile;

        private Buidler() {
        }

        public Buidler baseurl(String baseurl) {
            if (TextUtils.isEmpty(baseurl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseurl);
            return this;
        }

        // 用来处理http响应结果
        public Buidler globalHttpHandler(GlobalHttpHandler handler) {
            this.handler = handler;
            return this;
        }

        // 动态添加任意个interceptor
        public Buidler addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        // 处理所有Rxjava的onError逻辑
        public Buidler responseErrorListener(ResponseErrorListener listener) {
            this.responseErrorListener = listener;
            return this;
        }

        public Buidler cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
