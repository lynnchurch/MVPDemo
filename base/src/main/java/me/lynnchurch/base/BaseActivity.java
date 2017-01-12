package me.lynnchurch.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.lynnchurch.base.mvp.BasePresenter;

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    protected BaseApplication mApplication;
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    public static final String IS_IN_ACTIVITY_LIST = "isInActivityList";


    protected abstract void setApplication();

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        boolean notIn = false;
        if (getIntent() != null) {
            notIn = getIntent().getBooleanExtra(IS_IN_ACTIVITY_LIST, false);
        }

        if (!notIn) {
            mApplication.getActivityManager().addActivity(this);
        }

        setContentView(initView());
        mUnbinder = ButterKnife.bind(this);
        setApplication();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.getActivityManager().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mApplication.getActivityManager().getCurrentActivity() == this) {
            mApplication.getActivityManager().setCurrentActivity(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.getActivityManager().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (Unbinder.EMPTY != mUnbinder) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        mApplication = null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) {
            return view;
        }

        return super.onCreateView(name, context, attrs);
    }

    public void fullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    protected abstract View initView();

    protected abstract void initData();

}
