package com.syn.ad.synadapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.syn.ad.sdk.FBirdAd;
import com.syn.ad.sdk.listener.FBirdAdListener;
import com.syn.ad.sdk.listener.FBirdAdLoadListener;
import com.syn.ad.sdk.model.FBirdAdConfig;
import com.syn.ad.sdk.model.FBirdAdType;
import com.syn.ad.sdk.model.FBirdErrorModel;
import com.syn.ad.sdk.view.FBirdAdView;
import com.syn.ad.synad.testapp.R;

import java.util.List;

public class MainActivity extends Activity {


    FrameLayout fl_container; //广告容器
    FBirdAdView fBirdAdView;
    PermissionStatementDialog permissionStatementDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_container = findViewById(R.id.fl_container);

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
        findViewById(R.id.btn_sp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd(MainActivity.this, "1", FBirdAdType.SPLASH);
            }
        });

        findViewById(R.id.btn_feel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd(MainActivity.this, "2", FBirdAdType.FEED);
            }
        });

        findViewById(R.id.btn_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd(MainActivity.this, "1630", FBirdAdType.INTERSTITIAL);
            }
        });

        findViewById(R.id.btn_rew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd(MainActivity.this, "3", FBirdAdType.REWARD_VIDEO);
            }
        });
    }


    /**
     * 加载广告
     */
    private void loadAd(Context context, String postId, int adType) {
        FBirdAdConfig fBirdAdConfig = new FBirdAdConfig(context, postId, adType); // 这三个字段必填
        fBirdAdConfig.setAdCount(1); // 设置请求的广告数量。范围 1-3 。注意，该字段仅为预期，并不保证一定为该数量。举例：请求3条。实际可能只返回1条。以onAdLoadSuccess里的adViewList为准
        fBirdAdConfig.setShakeAble(true); //是否支持摇一摇。仅限 开屏。默认为true
        FBirdAd.loadAd(fBirdAdConfig, new FBirdAdLoadListener() {
            @Override
            public void onAdLoadSuccess(List<FBirdAdView> adViewList) {
                // 请求成功。
                FBirdAdView fBirdAdView = adViewList.get(0);
                fBirdAdView.setAdListener(new FBirdAdListener() {
                    @Override
                    public void onAdClose(FBirdAdView synAdView) {
                        // 关闭回调
                        AdLogUtil.log("ad close");
                    }

                    @Override
                    public void onAdShow() {
                        super.onAdShow();
                        // 显示回调
                        AdLogUtil.log("ad show");
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        // 点击回调
                        AdLogUtil.log("ad click");
                    }
                });
                fBirdAdView.show(fl_container);
            }

            @Override
            public void onAdLoadFail(FBirdErrorModel fBirdErrorModel) {
                super.onAdLoadFail(fBirdErrorModel);
                // 请求失败
                Toast.makeText(MainActivity.this,"广告加载失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 显示广告
     */
    private void showAd() {
        if (fBirdAdView == null || !fBirdAdView.isOk()) {
            Toast.makeText(MainActivity.this, "等待加载", Toast.LENGTH_LONG).show();
            return;
        }
        // show的参数在 插屏，激励视频可以为null 。其他类型必须传入，为展示广告的容器
        fBirdAdView.show(fl_container);
    }
}


