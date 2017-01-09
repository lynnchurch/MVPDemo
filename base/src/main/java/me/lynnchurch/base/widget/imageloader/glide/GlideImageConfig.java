package me.lynnchurch.base.widget.imageloader.glide;

import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import me.lynnchurch.base.widget.imageloader.ImageConfig;

public class GlideImageConfig extends ImageConfig {
    private DiskCacheStrategy mCacheStrategy;
    private BitmapTransformation mTransformation; // glide用它来改变图形的形状

    private GlideImageConfig(Buidler builder) {
        mUrl = builder.url;
        mImageView = builder.mImageView;
        mPlaceholder = builder.mPlaceholder;
        mErrorPic = builder.mErrorPic;
        mCacheStrategy = builder.mCacheStrategy;
        mTransformation = builder.mTransformation;
    }

    public DiskCacheStrategy getmCacheStrategy() {
        return mCacheStrategy;
    }

    public BitmapTransformation getmTransformation() {
        return mTransformation;
    }

    public static Buidler builder() {
        return new Buidler();
    }


    public static final class Buidler {
        private String url;
        private ImageView mImageView;
        private int mPlaceholder;
        private int mErrorPic;
        private DiskCacheStrategy mCacheStrategy;
        private BitmapTransformation mTransformation; // glide用它来改变图形的形状

        private Buidler() {
        }

        public Buidler url(String url) {
            this.url = url;
            return this;
        }

        public Buidler placeholder(int placeholder) {
            this.mPlaceholder = placeholder;
            return this;
        }

        public Buidler errorPic(int errorPic) {
            this.mErrorPic = errorPic;
            return this;
        }

        public Buidler imagerView(ImageView imageView) {
            this.mImageView = imageView;
            return this;
        }

        public Buidler cacheStrategy(DiskCacheStrategy cacheStrategy) {
            this.mCacheStrategy = cacheStrategy;
            return this;
        }

        public Buidler transformation(BitmapTransformation transformation) {
            this.mTransformation = transformation;
            return this;
        }

        public GlideImageConfig build() {
            if (url == null) {
                throw new IllegalStateException("mUrl is required");
            }
            if (mImageView == null) {
                throw new IllegalStateException("imageview is required");
            }
            return new GlideImageConfig(this);
        }
    }
}
