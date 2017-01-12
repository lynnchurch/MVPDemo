package me.lynnchurch.base.rxerrorhandler.handler;

import me.lynnchurch.base.rxerrorhandler.core.RxErrorHandler;
import rx.Subscriber;
import timber.log.Timber;

public abstract class ErrorHandlerSubscriber<T> extends Subscriber<T> {
    private ErrorHandlerFactory mHandlerFactory;

    public ErrorHandlerSubscriber(RxErrorHandler rxErrorHandler) {
        this.mHandlerFactory = rxErrorHandler.getmHandlerFactory();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e, e.getMessage());
        mHandlerFactory.handleError(e);
    }
}

