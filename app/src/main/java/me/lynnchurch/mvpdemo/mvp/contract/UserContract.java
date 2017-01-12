package me.lynnchurch.mvpdemo.mvp.contract;

import java.util.List;

import me.lynnchurch.base.DefaultAdapter;
import me.lynnchurch.base.mvp.BaseModel;
import me.lynnchurch.base.mvp.BaseView;
import me.lynnchurch.mvpdemo.mvp.model.api.cache.CacheManager;
import me.lynnchurch.mvpdemo.mvp.model.api.service.ServiceManager;
import me.lynnchurch.mvpdemo.mvp.model.bean.User;
import rx.Observable;

public interface UserContract {
    interface View extends BaseView {
        void setAdapter(DefaultAdapter adapter);

        void startLoadMore();

        void stopLoadMore();
    }

    abstract class Model extends BaseModel<ServiceManager, CacheManager> {
        public Model(ServiceManager serviceManager, CacheManager cacheManager) {
            super(serviceManager, cacheManager);
        }

        public abstract Observable<List<User>> getUsers(int lastIdQueried, boolean update);
    }
}
