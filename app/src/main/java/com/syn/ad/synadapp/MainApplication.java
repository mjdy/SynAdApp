package com.syn.ad.synadapp;

import android.app.Application;

import com.syn.ad.sdk.SynAd;
import com.syn.ad.sdk.model.SynSdkConfig;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SynSdkConfig synSdkConfig = new SynSdkConfig();
        SynAd.init(this, "123456", synSdkConfig);
    }
}
