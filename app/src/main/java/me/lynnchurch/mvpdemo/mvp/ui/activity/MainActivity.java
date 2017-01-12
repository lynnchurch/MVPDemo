package me.lynnchurch.mvpdemo.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.paginate.Paginate;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import me.lynnchurch.base.DefaultAdapter;
import me.lynnchurch.mvpdemo.R;
import me.lynnchurch.mvpdemo.di.component.AppComponent;
import me.lynnchurch.mvpdemo.di.component.DaggerUserComponent;
import me.lynnchurch.mvpdemo.di.module.UserModule;
import me.lynnchurch.mvpdemo.mvp.contract.UserContract;
import me.lynnchurch.mvpdemo.mvp.presenter.UserPresenter;
import rx.functions.Action1;
import timber.log.Timber;

public class MainActivity extends ComponentActivity<UserPresenter> implements UserContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Paginate mPaginate;
    private boolean isLoadingMore;

    @Override
    public void toFinish() {
        mPaginate = null;
        finish();
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
    }

    @Override
    protected void initData() {
        mPresenter.requestUsers(true);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestUsers(true);
    }

    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        configRecycleView(mRecyclerView, new GridLayoutManager(this, 2));
    }

    private void configRecycleView(RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestUsers(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void setAdapter(DefaultAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        initRecycleView();
        initPaginate();
    }

    @Override
    public void requestPermissions(Activity activity) {
        new RxPermissions(this)
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            Timber.i("Permission is granted");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Timber.i("Denied permission without ask never again");
                        } else {
                            Timber.i("Denied permission with ask never again need to go to the settings");
                        }
                    }
                });
    }

    @Override
    public void showLoading() {
        Timber.i("showLoading");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        Timber.i("hideLoading");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void stopLoadMore() {
        isLoadingMore = false;
    }
}
