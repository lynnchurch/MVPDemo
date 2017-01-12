package me.lynnchurch.mvpdemo.di.module;

import dagger.Module;
import dagger.Provides;
import me.lynnchurch.base.di.scope.ActivityScope;
import me.lynnchurch.mvpdemo.mvp.contract.UserContract;
import me.lynnchurch.mvpdemo.mvp.model.UserModel;

@Module
public class UserModule {
    private UserContract.View mView;

    public UserModule(UserContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    UserContract.View provideUserView() {
        return mView;
    }

    @ActivityScope
    @Provides
    UserContract.Model provideUserModel(UserModel model) {
        return model;
    }
}
