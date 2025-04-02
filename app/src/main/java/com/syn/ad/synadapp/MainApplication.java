package com.syn.ad.synadapp;

import android.app.Application;

import cn.cusky.ad.sdk.SynAd;
import cn.cusky.ad.sdk.model.SynSdkConfig;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SynSdkConfig synSdkConfig = new SynSdkConfig();
        // 隐私权限设置。 1是允许，0是拒绝。 默认均是允许。如果有些字段拒绝sdk获取，建议手动传入
        synSdkConfig.setCanUsePhoneState(SynSdkConfig.PERMISSION_ON);
        synSdkConfig.setCanUseAppList(SynSdkConfig.PERMISSION_OFF);

        synSdkConfig.setOaid("your oaid"); // 务必传入

//        synSdkConfig.setCanUseAndroidId(SynSdkConfig.PERMISSION_OFF); // 拒绝androidId获取
//        synSdkConfig.setAndroidId("your androidId"); // 如果上面拒绝androidId获取，建议手动写入androidId

        SynAd.init(this, "9xf1IfFT", synSdkConfig);

    }
}
