package com.syn.ad.synadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import java.util.List;

import cn.cusky.ad.sdk.SynAd;
import cn.cusky.ad.sdk.listener.SynAdLoadListener;
import cn.cusky.ad.sdk.listener.SynAdViewEventListener;
import cn.cusky.ad.sdk.model.SynAdConfig;
import cn.cusky.ad.sdk.model.SynAdRenderModel;
import cn.cusky.ad.sdk.model.SynAdType;
import cn.cusky.ad.sdk.model.SynClickModel;
import cn.cusky.ad.sdk.model.SynErrorModel;
import cn.cusky.ad.sdk.model.SynSdkConfig;
import cn.cusky.ad.sdk.view.SynAdInstance;

public class MainActivity extends Activity {


    FrameLayout fl_container; //广告容器
    SynAdInstance synAdInstance;
    PermissionStatementDialog permissionStatementDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean privacyAgree = SPUtils.getInstance().getBoolean(MainApplication.PRIVACY_AGREE, false);
        if (!privacyAgree) {
            permissionStatementDialog = new PermissionStatementDialog(this, new PermissionStatementDialog.ConfirmedCallback() {
                @Override
                public void onConfirmed() {
                    SPUtils.getInstance().put(MainApplication.PRIVACY_AGREE, true);
                    MainApplication.get().initOtherSdk();
                }

                @Override
                public void onCancel() {

                }
            });
            permissionStatementDialog.show();
        }
        fl_container = findViewById(R.id.fl_container);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtils.getInstance().getBoolean(MainApplication.PRIVACY_AGREE, false)) {
                    loadAd();
                } else {
                    Toast.makeText(MainActivity.this, "广告SDK未初始化", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.btn_load_sync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtils.getInstance().getBoolean(MainApplication.PRIVACY_AGREE, false)) {
                    loadAdSync();
                } else {
                    Toast.makeText(MainActivity.this, "广告SDK未初始化", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();

            }
        });
    }


    /**
     * 异步加载广告
     */
    private void loadAd() {
        SynAdConfig synAdConfig = new SynAdConfig(this, "3", SynAdType.DRAW); // 这三个字段必填
        SynAd.loadAd(synAdConfig, new SynAdLoadListener() {
            @Override
            public void onAdLoadSuccess(List<SynAdInstance> adInstanceList) {
                // 请求成功。
                synAdInstance = adInstanceList.get(0);
            }

            @Override
            public void onAdLoadFail(SynErrorModel synErrorModel) {
                super.onAdLoadFail(synErrorModel);
                // 请求失败
                Toast.makeText(MainActivity.this, "请求广告失败", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 同步加载广告
     */
    private void loadAdSync() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 里面有网络请求，要放在线程里运行
                SynAdConfig synAdConfig = new SynAdConfig(MainActivity.this, "3", SynAdType.DRAW); // 这三个字段必填
                synAdInstance = SynAd.loadAdSync(synAdConfig);

            }
        }).start();

    }

    /**
     * 显示广告
     */
    private void showAd() {
        if (synAdInstance == null || !synAdInstance.isOk()) {
            Toast.makeText(MainActivity.this, "等待加载", Toast.LENGTH_LONG).show();
            return;
        }

        // 获取物料model
        SynAdRenderModel renderModel = synAdInstance.getAdData();
        if (renderModel == null) {
            Toast.makeText(MainActivity.this, "物料获取异常", Toast.LENGTH_LONG).show();
            return;
        }

        AdLogUtil.log(renderModel.toString());

        // 显示view
        fl_container.removeAllViews();
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(400, 800));
        Glide.with(this).load(renderModel.imgUrl).into(imageView);
        fl_container.addView(imageView);

        // 绑定展示广告的view。 内部会处理点击事件
        synAdInstance.bindView(fl_container);

        // 如果需要对 fl_container 的点击回传，用下面的方法
//        synAdInstance.bindView(fl_container, new SynAdViewEventListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//
//            @Override
//            public void onTouch(View view, MotionEvent motionEvent) {
//
//            }
//        });

        // TODO 曝光时要执行. 务必
        synAdInstance.callbackShow();

    }

    public void initOtherSdk(Application application) {
        SynSdkConfig synSdkConfig = new SynSdkConfig();
        // 隐私权限设置。 1是允许，0是拒绝。 默认均是允许。如果有些字段拒绝sdk获取，建议手动传入
        synSdkConfig.setCanUsePhoneState(SynSdkConfig.PERMISSION_ON);
        synSdkConfig.setCanUseAppList(SynSdkConfig.PERMISSION_OFF);

        synSdkConfig.setOaid("your oaid"); // 务必传入

//        synSdkConfig.setCanUseAndroidId(SynSdkConfig.PERMISSION_OFF); // 拒绝androidId获取
//        synSdkConfig.setAndroidId("your androidId"); // 如果上面拒绝androidId获取，建议手动写入androidId

        SynAd.init(application, "9xf1IfFT", synSdkConfig);
    }
}


