package me.lynnchurch.base.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.lynnchurch.base.widget.imageloader.BaseImageLoaderStrategy;
import me.lynnchurch.base.widget.imageloader.glide.GlideImageLoaderStrategy;

@Module
public class ImageModule {
    @Singleton
    @Provides
    public BaseImageLoaderStrategy provideImageLoaderStrategy(GlideImageLoaderStrategy glideImageLoaderStrategy) {
        return glideImageLoaderStrategy;
    }
}
