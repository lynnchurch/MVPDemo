package me.lynnchurch.mvpdemo.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import me.lynnchurch.base.BaseHolder;
import me.lynnchurch.base.widget.imageloader.ImageLoader;
import me.lynnchurch.base.widget.imageloader.glide.GlideImageConfig;
import me.lynnchurch.mvpdemo.R;
import me.lynnchurch.mvpdemo.app.DemoApplication;
import me.lynnchurch.mvpdemo.mvp.model.bean.User;
import rx.Observable;

public class UserItemHolder extends BaseHolder<User> {

    @Nullable
    @BindView(R.id.iv_avatar)
    ImageView mAvater;
    @Nullable
    @BindView(R.id.tv_name)
    TextView mName;
    private ImageLoader mImageLoader;
    private final DemoApplication mApplication;

    public UserItemHolder(View itemView) {
        super(itemView);
        mApplication = (DemoApplication) itemView.getContext().getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public void setData(User data) {
        Observable.just(data.getLogin())
                .subscribe(RxTextView.text(mName));

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .cacheStrategy(DiskCacheStrategy.ALL)
                .url(data.getAvatarUrl())
                .imagerView(mAvater)
                .build());
    }
}
