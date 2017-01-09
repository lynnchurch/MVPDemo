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

import me.lynnchurch.base.mvp.Presenter;

public abstract class BaseActivity<P extends Presenter> extends RxAppCompatActivity {
    protected BaseApplication mApplication;
    @Inject
    protected P mPresenter;

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    public static final String IS_IN_ACTIVITY_LIST = "isInActivityList";


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


    @Override
    protected void onResume() {
        super.onResume();
        mApplication.getAppManager().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mApplication.getAppManager().getCurrentActivity() == this) {
            mApplication.getAppManager().setCurrentActivity(null);
        }
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时退出此activity就在intent加此字段为true)
        boolean notIn = false;
        if (getIntent() != null) {
            notIn = getIntent().getBooleanExtra(IS_IN_ACTIVITY_LIST, false);
        }

        if (!notIn) {
            mApplication.getAppManager().addActivity(this);
        }

        setContentView(initView());
        ComponentInject();//依赖注入
        initData();
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();

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
    protected void onDestroy() {
        super.onDestroy();
        mApplication.getAppManager().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.onDestroy(); //释放资源
        }
        this.mPresenter = null;
        this.mApplication = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    protected abstract View initView();

    protected abstract void initData();

}
