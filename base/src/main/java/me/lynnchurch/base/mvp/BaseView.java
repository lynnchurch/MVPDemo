package me.lynnchurch.base.mvp;

public interface BaseView {

    /**
     * 申请权限
     */
    void requestPermissions();

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);

    /**
     * 结束
     */
    void finish();
}
