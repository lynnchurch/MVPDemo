package me.lynnchurch.base.widget.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.lynnchurch.base.widget.imageloader.BaseImageLoaderStrategy;

@Singleton
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

    @Inject
    public GlideImageLoaderStrategy() {
    }

    @Override
    public void loadImage(Context ctx, final GlideImageConfig config) {
        RequestManager manager = Glide.with(ctx);

        DrawableRequestBuilder<String> requestBuilder = manager.load(config.getUrl())
                .centerCrop()
                .crossFade(500);

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
                .into(new ImageViewTarget<GlideDrawable>(config.getImageView()) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        config.getImageView().setImageDrawable(resource);
                    }
                });
    }
}
