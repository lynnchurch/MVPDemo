package me.lynnchurch.mvpdemo.app;

import android.content.Context;
import android.text.TextUtils;

import me.lynnchurch.base.BaseApplication;
import me.lynnchurch.base.di.module.HttpConfigModule;
import me.lynnchurch.base.http.GlobalHttpHandler;
import me.lynnchurch.base.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.lynnchurch.mvpdemo.BuildConfig;
import me.lynnchurch.mvpdemo.di.component.AppComponent;
import me.lynnchurch.mvpdemo.di.component.DaggerAppComponent;
import me.lynnchurch.mvpdemo.di.module.CacheModule;
import me.lynnchurch.mvpdemo.di.module.ServiceModule;
import me.lynnchurch.mvpdemo.mvp.model.api.API;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

public class DemoApplication extends BaseApplication {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(getAppModule())
                .clientModule(getClientModule())
                .imageModule(getImageModule())
                .httpConfigModule(getHttpConfigModule())
                .serviceModule(new ServiceModule())
                .cacheModule(new CacheModule())
                .build();

        if (BuildConfig.LOG_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mAppComponent = null;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    protected HttpConfigModule getHttpConfigModule() {
        return HttpConfigModule
                .buidler()
                .baseurl(API.APP_DOMAIN)
                .addInterceptor(getLoggingInterceptor())
                .globalHttpHandler(new GlobalHttpHandler() {
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        Timber.i("request will be sent");
                        return request;
                    }

                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        if (!TextUtils.isEmpty(httpResult)) {
                            Timber.i("response data size: %d KB", httpResult.getBytes().length/1024);
                        }
                        // 这里如果发现token过期,可以先请求最新的token,然后拿最新的token重新请求
                        // 注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp去请求
                        // Request newRequest = chain.request().newBuilder().header("token", newToken).build();
                        // retry the request
                        // response.body().close();
                        // 新的请求成功后,将返回的response  return出去即可
                        return response;
                    }
                })
                .responseErrorListener(new ResponseErrorListener() {
                    @Override
                    public void handleResponseError(Context context, Exception e) {
                        Timber.e(e.getMessage());
                    }
                }).build();
    }

    /**
     * 获取网络请求日志拦截器
     *
     * @return
     */
    public Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.LOG_DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return logging;
    }
}
