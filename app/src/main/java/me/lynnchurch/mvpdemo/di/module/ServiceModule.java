package me.lynnchurch.mvpdemo.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.lynnchurch.mvpdemo.mvp.model.api.service.CommonService;
import me.lynnchurch.mvpdemo.mvp.model.api.service.UserService;
import retrofit2.Retrofit;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

    @Singleton
    @Provides
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

}
