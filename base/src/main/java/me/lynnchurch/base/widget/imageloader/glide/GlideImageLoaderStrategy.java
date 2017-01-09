package me.lynnchurch.base.widget.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.lynnchurch.base.widget.imageloader.BaseImageLoaderStrategy;

/**
 * Created by jess on 8/5/16 16:28
 * contact with jess.yan.effort@gmail.com
 */
@Singleton
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

    @Inject
    public GlideImageLoaderStrategy() {
    }

    @Override
    public void loadImage(Context ctx, GlideImageConfig config) {
        RequestManager manager = Glide.with(ctx);

        DrawableRequestBuilder<String> requestBuilder = manager.load(config.getUrl())
                .crossFade()
                .centerCrop();

        switch (config.getmCacheStrategy()) {
            case ALL:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case NONE:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case SOURCE:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
            case RESULT:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
        }

        if (config.getmTransformation() != null) {
            requestBuilder.transform(config.getmTransformation()); // glide用它来改变图形的形状
        }

        if (config.getPlaceholder() != 0) {
            requestBuilder.placeholder(config.getPlaceholder()); // 设置占位符
        }

        if (config.getErrorPic() != 0) {
            requestBuilder.error(config.getErrorPic()); // 设置错误的图片
        }

        requestBuilder
                .into(config.getImageView());
    }
}
