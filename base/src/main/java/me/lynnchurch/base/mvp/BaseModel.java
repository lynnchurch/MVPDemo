package me.lynnchurch.base.mvp;

import me.lynnchurch.base.http.BaseCacheManager;
import me.lynnchurch.base.http.BaseServiceManager;

public abstract class BaseModel<S extends BaseServiceManager, C extends BaseCacheManager> {
    protected S mServiceManager;
    protected C mCacheManager;

    public BaseModel(S serviceManager, C cacheManager) {
        this.mServiceManager = serviceManager;
        this.mCacheManager = cacheManager;
    }

    public void onDestory() {
        if (null != mServiceManager) {
            mServiceManager.onDestory();
            mServiceManager = null;
        }
        if (null != mCacheManager) {
            mCacheManager.onDestory();
            mCacheManager = null;
        }
    }
}
