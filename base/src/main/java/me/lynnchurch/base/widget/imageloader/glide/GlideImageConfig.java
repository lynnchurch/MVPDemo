package me.lynnchurch.base.widget.imageloader.glide;

import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import me.lynnchurch.base.widget.imageloader.ImageConfig;

public class GlideImageConfig extends ImageConfig {
    private DiskCacheStrategy mCacheStrategy;
    private BitmapTransformation mTransformation;

    private GlideImageConfig(Buidler builder) {
        mUrl = builder.mUrl;
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
        private String mUrl;
        private ImageView mImageView;
        private int mPlaceholder;
        private int mErrorPic;
        private int mWidth;
        private int mHeight;
        private DiskCacheStrategy mCacheStrategy;
        private BitmapTransformation mTransformation;

        private Buidler() {
        }

        public Buidler url(String url) {
            mUrl = url;
            return this;
        }

        public Buidler placeholder(int placeholder) {
            mPlaceholder = placeholder;
            return this;
        }

        public Buidler errorPic(int errorPic) {
            mErrorPic = errorPic;
            return this;
        }

        public Buidler resize(int width, int height) {
            mWidth = width;
            mHeight = height;
            return this;
        }

        public Buidler imageView(ImageView imageView) {
            mImageView = imageView;
            return this;
        }

        public Buidler cacheStrategy(DiskCacheStrategy cacheStrategy) {
            mCacheStrategy = cacheStrategy;
            return this;
        }

        public Buidler transformation(BitmapTransformation transformation) {
            mTransformation = transformation;
            return this;
        }

        public GlideImageConfig build() {
            if (mUrl == null) {
                throw new IllegalStateException("mUrl is required");
            }
            if (mImageView == null) {
                throw new IllegalStateException("imageview is required");
            }
            return new GlideImageConfig(this);
        }
    }
}
