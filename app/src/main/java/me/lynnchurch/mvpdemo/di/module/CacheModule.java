package me.lynnchurch.mvpdemo.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import me.lynnchurch.mvpdemo.mvp.model.api.cache.UserCache;

@Module
public class CacheModule {

    @Singleton
    @Provides
    UserCache provideUserCache(RxCache rxCache) {
        return rxCache.using(UserCache.class);
    }
}
