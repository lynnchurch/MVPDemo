package me.lynnchurch.base.rxerrorhandler.handler;

import android.content.Context;

import me.lynnchurch.base.rxerrorhandler.handler.listener.ResponseErrorListener;

public class ErrorHandlerFactory {
    private Context mContext;
    private ResponseErrorListener mResponseErroListener;

    public ErrorHandlerFactory(Context mContext, ResponseErrorListener mResponseErroListener) {
        this.mResponseErroListener = mResponseErroListener;
        this.mContext = mContext;
    }

    public void handleError(Throwable throwable) {
        mResponseErroListener.handleResponseError(mContext, (Exception) throwable);
    }
}
