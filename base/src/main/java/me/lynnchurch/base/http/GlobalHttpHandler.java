package me.lynnchurch.base.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public interface GlobalHttpHandler {
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
            return null;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            return null;
        }
    };

}
