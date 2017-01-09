package me.lynnchurch.base.rxerrorhandler.core;

import android.content.Context;

import me.lynnchurch.base.rxerrorhandler.handler.ErrorHandlerFactory;
import me.lynnchurch.base.rxerrorhandler.handler.listener.ResponseErrorListener;

import static me.lynnchurch.base.rxerrorhandler.utils.Preconditions.checkNotNull;

public class RxErrorHandler {
    private ErrorHandlerFactory mHandlerFactory;

    private RxErrorHandler(Builder builder) {
        this.mHandlerFactory = builder.errorHandlerFactory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ErrorHandlerFactory getmHandlerFactory() {
        return mHandlerFactory;
    }

    public static final class Builder {
        private Context context;
        private ResponseErrorListener responseErroListener;
        private ErrorHandlerFactory errorHandlerFactory;

        private Builder() {
        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder responseErroListener(ResponseErrorListener responseErroListener) {
            this.responseErroListener = responseErroListener;
            return this;
        }

        public RxErrorHandler build() {
            checkNotNull(context, "context is required");
            checkNotNull(responseErroListener, "responseErroListener is required");

            this.errorHandlerFactory = new ErrorHandlerFactory(context, responseErroListener);

            return new RxErrorHandler(this);
        }
    }
}
