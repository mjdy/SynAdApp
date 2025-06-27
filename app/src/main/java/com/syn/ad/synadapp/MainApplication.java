package com.syn.ad.synadapp;

import android.app.Application;

import com.syn.ad.sdk.FBirdAd;
import com.syn.ad.sdk.model.FBirdSdkConfig;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FBirdSdkConfig fBirdSdkConfig = new FBirdSdkConfig();
        // 隐私权限设置。 1是允许，0是拒绝。 默认均是允许
        fBirdSdkConfig.setUseAndroidId(FBirdSdkConfig.PERMISSION_ON);
        fBirdSdkConfig.setUseAppList(FBirdSdkConfig.PERMISSION_OFF);
        fBirdSdkConfig.setUseMacAddress(FBirdSdkConfig.PERMISSION_OFF);
//        synSdkConfig.setOaid("your oaid");

        FBirdAd.initSdkConfig(this, "appId", fBirdSdkConfig);
    }
}
