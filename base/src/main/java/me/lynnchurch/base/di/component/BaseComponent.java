package me.lynnchurch.base.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.lynnchurch.base.BaseApplication;
import me.lynnchurch.base.di.module.AppModule;

@Singleton
@Component(modules = {AppModule.class})
public interface BaseComponent {
    void inject(BaseApplication application);
}
