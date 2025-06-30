package com.syn.ad.synadapp;

import android.app.Application;
import android.content.Context;

import com.syn.ad.sdk.FBirdAd;
import com.syn.ad.sdk.model.FBirdSdkConfig;


public class MainApplication extends Application {

    private static Context sContext;

    public static MainApplication app;
    public static final String PRIVACY_AGREE = "privacy_agree";

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        app = this;
        boolean privacyAgree = SPUtils.getInstance().getBoolean(PRIVACY_AGREE, false);
        if (privacyAgree) {
            initOtherSdk();
        }

    }

    public static MainApplication get() {
        if (app == null) {
            synchronized (MainApplication.class) {
                if (app == null) {
                    new MainApplication();
                }
            }
        }
        return app;
    }


    public static Context getContext() {
        return sContext;
    }


    public void initOtherSdk() {
        FBirdSdkConfig fBirdSdkConfig = new FBirdSdkConfig();
        // 隐私权限设置。 1是允许，0是拒绝。 默认均是允许
        fBirdSdkConfig.setUseAndroidId(FBirdSdkConfig.PERMISSION_ON);
        fBirdSdkConfig.setUseAppList(FBirdSdkConfig.PERMISSION_OFF);
        fBirdSdkConfig.setUseMacAddress(FBirdSdkConfig.PERMISSION_OFF);
//        synSdkConfig.setOaid("your oaid");

        FBirdAd.initSdkConfig(this, "appId", fBirdSdkConfig);
    }
}
