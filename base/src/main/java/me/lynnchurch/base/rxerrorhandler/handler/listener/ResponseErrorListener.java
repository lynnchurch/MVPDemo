package me.lynnchurch.base.rxerrorhandler.handler.listener;

import android.content.Context;

public interface ResponseErrorListener {
    void handleResponseError(Context context, Exception e);

    ResponseErrorListener EMPTY = new ResponseErrorListener() {
        @Override
        public void handleResponseError(Context context, Exception e) {

        }
    };
}
