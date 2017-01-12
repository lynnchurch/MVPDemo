package me.lynnchurch.mvpdemo.di.component;

import dagger.Component;
import me.lynnchurch.base.di.scope.ActivityScope;
import me.lynnchurch.mvpdemo.di.module.UserModule;
import me.lynnchurch.mvpdemo.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = UserModule.class,dependencies = AppComponent.class)
public interface UserComponent {
    void inject(MainActivity activity);
}
