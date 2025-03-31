package com.syn.ad.synadapp;

import android.app.Application;

import com.syn.ad.sdk.SynAd;
import com.syn.ad.sdk.model.SynSdkConfig;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SynSdkConfig synSdkConfig = new SynSdkConfig();
        // 隐私权限设置。 1是允许，0是拒绝。 默认均是允许
        synSdkConfig.setCanUsePhoneState(SynSdkConfig.PERMISSION_ON);
        synSdkConfig.setCanUseAppList(SynSdkConfig.PERMISSION_OFF);

        synSdkConfig.setOaid("your oaid"); // 务必传入

        SynAd.init(this, "9xf1IfFT", synSdkConfig);
    }
}
