package me.lynnchurch.mvpdemo.mvp.model.api.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.lynnchurch.base.http.BaseServiceManager;

@Singleton
public class ServiceManager implements BaseServiceManager {
    private CommonService mCommonService;
    private UserService mUserService;

    @Inject
    public ServiceManager(CommonService commonService,UserService userService){
        mCommonService = commonService;
        mUserService = userService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }

    public UserService getUserService() {
        return mUserService;
    }

    @Override
    public void onDestory() {

    }
}
