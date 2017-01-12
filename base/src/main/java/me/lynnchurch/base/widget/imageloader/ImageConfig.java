package me.lynnchurch.base.widget.imageloader;

import android.widget.ImageView;

public class ImageConfig {
    protected String mUrl;
    protected ImageView mImageView;
    protected int mPlaceholder;
    protected int mErrorPic;

    public String getUrl() {
        return mUrl;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public int getPlaceholder() {
        return mPlaceholder;
    }

    public int getErrorPic() {
        return mErrorPic;
    }
}
