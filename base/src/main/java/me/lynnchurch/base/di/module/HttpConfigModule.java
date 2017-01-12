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

import static me.lynnchurch.base.rxerrorhandler.utils.Preconditions.checkNotNull;

@Module
public class HttpConfigModule {
    private HttpUrl mApiUrl;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private File mCacheFile;

    private HttpConfigModule(Buidler buidler) {
        mApiUrl = buidler.mApiUrl;
        mHandler = buidler.mHandler;
        mInterceptors = buidler.mInterceptors;
        mErrorListener = buidler.mResponseErrorListener;
        mCacheFile = buidler.mCacheFile;
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
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mHandler == null ? GlobalHttpHandler.EMPTY : mHandler;
    }

    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return mCacheFile == null ? ConfigHelper.getCacheDir(application) : mCacheFile;
    }

    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    public static final class Buidler {
        private HttpUrl mApiUrl = HttpUrl.parse("https://api.github.com/");
        private GlobalHttpHandler mHandler;
        private List<Interceptor> mInterceptors = new ArrayList<>();
        private ResponseErrorListener mResponseErrorListener;
        private File mCacheFile;

        private Buidler() {
        }

        public Buidler baseurl(String baseurl) {
            if (TextUtils.isEmpty(baseurl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            mApiUrl = HttpUrl.parse(baseurl);
            return this;
        }

        public Buidler globalHttpHandler(GlobalHttpHandler handler) {
            mHandler = handler;
            return this;
        }

        public Buidler addInterceptor(Interceptor interceptor) {
            mInterceptors.add(interceptor);
            return this;
        }

        public Buidler responseErrorListener(ResponseErrorListener listener) {
            mResponseErrorListener = listener;
            return this;
        }

        public Buidler cacheFile(File cacheFile) {
            mCacheFile = cacheFile;
            return this;
        }

        public HttpConfigModule build() {
            checkNotNull(mApiUrl, "baseurl is required");
            return new HttpConfigModule(this);
        }
    }
}
