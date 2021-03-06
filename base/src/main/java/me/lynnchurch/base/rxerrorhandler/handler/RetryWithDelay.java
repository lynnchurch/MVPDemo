package me.lynnchurch.base.rxerrorhandler.handler;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

public class RetryWithDelay implements
        Func1<Observable<? extends Throwable>, Observable<?>> {
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (++retryCount <= maxRetries) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            Timber.e(throwable, "get error:\n%s, it will try after " + retryDelaySecond
                                    + " second, retry count " + retryCount, throwable.getMessage());
                            return Observable.timer(retryDelaySecond,
                                    TimeUnit.SECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}