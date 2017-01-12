package me.lynnchurch.mvpdemo.mvp.model;

import java.util.List;
import javax.inject.Inject;
import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import me.lynnchurch.base.di.scope.ActivityScope;
import me.lynnchurch.mvpdemo.mvp.contract.UserContract;
import me.lynnchurch.mvpdemo.mvp.model.api.cache.CacheManager;
import me.lynnchurch.mvpdemo.mvp.model.api.service.ServiceManager;
import me.lynnchurch.mvpdemo.mvp.model.bean.User;
import rx.Observable;
import rx.functions.Func1;

@ActivityScope
public class UserModel extends UserContract.Model {
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public UserModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


    @Override
    public Observable<List<User>> getUsers(int lastIdQueried, boolean update) {
        Observable<List<User>> users = mServiceManager.getUserService()
                .getUsers(lastIdQueried, USERS_PER_PAGE);
        return mCacheManager.getUserCache()
                .getUsers(users
                        , new DynamicKey(lastIdQueried)
                        , new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<List<User>>, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(Reply<List<User>> listReply) {
                        return Observable.just(listReply.getData());
                    }
                });
    }
}
