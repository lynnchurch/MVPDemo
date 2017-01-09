package me.lynnchurch.base.http;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

import static me.lynnchurch.base.utils.CharactorHandler.jsonFormat;

@Singleton
public class RequestIntercept implements Interceptor {
    private GlobalHttpHandler mHandler;

    @Inject
    public RequestIntercept(GlobalHttpHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (mHandler != null) {
            request = mHandler.onHttpRequestBefore(chain, request);
        }

        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        } else {
            Timber.w("request.body() == null");
        }

        // 打印url信息
        Timber.tag("Request").w("Sending Request %s on %n Params --->  %s%n Connection ---> %s%n Headers ---> %s", request.url()
                , request.body() != null ? parseParams(request.body(), requestbuffer) : "null"
                , chain.connection()
                , request.headers());

        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();
        // 打印响应时间
        Timber.tag("Response").w("Received response  in %.1fms%n%s", (t2 - t1) / 1e6d, originalResponse.headers());

        // 读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        // 获取content的压缩类型
        String encoding = originalResponse
                .headers()
                .get("Content-Encoding");

        String bodyString;

        // 解析response content
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        bodyString = buffer.readString(charset);

        Timber.tag("Result").w(jsonFormat(bodyString));

        if (mHandler != null) { // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);
        }

        return originalResponse;
    }

    @NonNull
    public static String parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }
}
