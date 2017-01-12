package me.lynnchurch.mvpdemo.mvp.model.api.cache;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.lynnchurch.base.http.BaseCacheManager;

@Singleton
public class CacheManager implements BaseCacheManager {
    private UserCache mUserCache;

    @Inject
    public CacheManager(UserCache userCache) {
        mUserCache = userCache;
    }

    public UserCache getUserCache() {
        return mUserCache;
    }

    @Override
    public void onDestory() {

    }
}
