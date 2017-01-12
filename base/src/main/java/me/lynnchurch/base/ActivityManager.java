package me.lynnchurch.base;

import android.app.Application;
import android.content.Intent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class ActivityManager {
    private Application mApplication;

    // 所有Activity
    public List<BaseActivity> mActivityList;
    // 当前在前台的Activity
    private BaseActivity mCurrentActivity;

    @Inject
    public ActivityManager(Application application) {
        this.mApplication = application;
    }

    /**
     * 让在前台的Activity打开下一个Activity
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getCurrentActivity() == null) {
            Timber.e("mCurrentActivity == null when startActivity(Intent)");
            // 如果没有前台的Activity就使用new_task模式启动Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mApplication.startActivity(intent);
            return;
        }
        getCurrentActivity().startActivity(intent);
    }

    /**
     * 让在前台的Activity打开下一个Activity
     *
     * @param ActivityClass
     */
    public void startActivity(Class ActivityClass) {
        startActivity(new Intent(mApplication, ActivityClass));
    }

    /**
     * 释放资源
     */
    public void release() {
        mActivityList.clear();
        mActivityList = null;
        mCurrentActivity = null;
        mApplication = null;
    }

    /**
     * 将在前台的Activity保存
     *
     * @param currentActivity
     */
    public void setCurrentActivity(BaseActivity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * 获得当前在前台的Activity
     *
     * @return
     */
    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 返回一个Activity集合
     *
     * @return
     */
    public List<BaseActivity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 添加Activity到集合
     */
    public void addActivity(BaseActivity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (ActivityManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    /**
     * 删除集合里的指定Activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        if (mActivityList == null) {
            Timber.w("mActivityList == null when removeActivity(BaseActivity)");
            return;
        }
        synchronized (ActivityManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 删除集合里的指定位置的Activity
     *
     * @param location
     */
    public BaseActivity removeActivity(int location) {
        if (mActivityList == null) {
            Timber.w("mActivityList == null when removeActivity(int)");
            return null;
        }
        synchronized (ActivityManager.class) {
            if (location > 0 && location < mActivityList.size()) {
                return mActivityList.remove(location);
            }
        }
        return null;
    }

    /**
     * 关闭指定Activity
     *
     * @param ActivityClass
     */
    public void killActivity(Class<?> ActivityClass) {
        if (mActivityList == null) {
            Timber.w("mActivityList == null when killActivity");
            return;
        }
        for (BaseActivity Activity : mActivityList) {
            if (Activity.getClass().equals(ActivityClass)) {
                Activity.finish();
            }
        }
    }

    /**
     * 指定的Activity实例是否存活
     *
     * @param Activity
     * @return
     */
    public boolean isActivityInstanceLive(BaseActivity Activity) {
        if (mActivityList == null) {
            Timber.w("mActivityList == null when ActivityInstanceIsLive");
            return false;
        }
        return mActivityList.contains(Activity);
    }

    /**
     * 指定的Activity class是否存活(一个Activity可能有多个实例)
     *
     * @param ActivityClass
     * @return
     */
    public boolean isActivityClassLive(Class<?> ActivityClass) {
        if (mActivityList == null) {
            Timber.w("mActivityList == null when ActivityClassIsLive");
            return false;
        }
        for (BaseActivity Activity : mActivityList) {
            if (Activity.getClass().equals(ActivityClass)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 关闭所有Activity
     */
    public void killAll() {
        Iterator<BaseActivity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
            iterator.remove();
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            killAll();
            mActivityList = null;
            System.exit(0);
        } catch (Exception e) {
            Timber.e(e, e.getMessage());
        }
    }
}
