package me.lynnchurch.base.mvp;

import android.app.Activity;

public interface BaseView {

    void requestPermissions(Activity activity);

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void toFinish();
}
