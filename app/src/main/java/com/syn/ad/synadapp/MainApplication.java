package com.syn.ad.synadapp;

import android.app.Application;
import android.content.Context;

import cn.cusky.ad.sdk.SynAd;
import cn.cusky.ad.sdk.model.SynSdkConfig;


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
        if(privacyAgree) {
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

    public void initOtherSdk(){
        SynSdkConfig synSdkConfig = new SynSdkConfig();
        // 隐私权限设置。 1是允许，0是拒绝。 默认均是不允许。如果有些字段拒绝sdk获取，建议手动传入

        //  IMEI（硬件序列号）权限设置。 1是允许，0是拒绝。
        synSdkConfig.setCanUsePhoneState(SynSdkConfig.PERMISSION_OFF);
        //  ANDROID_ID权限设置。 1是允许，0是拒绝。
        synSdkConfig.setCanUseAndroidId(SynSdkConfig.PERMISSION_OFF);
        //  MacAddress（BSSID，SSID）权限设置。 1是允许，0是拒绝。
        synSdkConfig.setCanUseMacAddress(SynSdkConfig.PERMISSION_OFF);
        // 获取wifi状态权限设置。 1是允许，0是拒绝。
        synSdkConfig.setCanUseWifiState(SynSdkConfig.PERMISSION_OFF);
        // IMSI权限设置。 1是允许，0是拒绝。
        synSdkConfig.setCanUseImsi(SynSdkConfig.PERMISSION_OFF);
        synSdkConfig.setOaid("your oaid"); // 务必传入

//        synSdkConfig.setCanUseAndroidId(SynSdkConfig.PERMISSION_OFF); // 拒绝androidId获取
//        synSdkConfig.setAndroidId("your androidId"); // 如果上面拒绝androidId获取，建议手动写入androidId

        SynAd.init(this, "9xf1IfFT", synSdkConfig);
    }
}
