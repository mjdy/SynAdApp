package com.syn.ad.synadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.syn.ad.sdk.SynAd;
import com.syn.ad.sdk.listener.SynAdListener;
import com.syn.ad.sdk.listener.SynAdLoadListener;
import com.syn.ad.sdk.model.SynAdConfig;
import com.syn.ad.sdk.model.SynAdType;
import com.syn.ad.sdk.model.SynErrorModel;
import com.syn.ad.sdk.view.SynAdView;

import java.util.List;

public class MainActivity extends Activity {


    FrameLayout fl_container; //广告容器
    SynAdView synAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_container = findViewById(R.id.fl_container);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd();
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
     * 加载广告
     */
    private void loadAd() {
        SynAdConfig synAdConfig = new SynAdConfig(this, "1", SynAdType.FEED); // 这三个字段必填
        synAdConfig.setAdCount(1); // 设置请求的广告数量。范围 1-3 。注意，该字段仅为预期，并不保证一定为该数量。举例：请求3条。实际可能只返回1条。以onAdLoadSuccess里的adViewList为准
        SynAd.loadAd(synAdConfig, new SynAdLoadListener() {
            @Override
            public void onAdLoadSuccess(List<SynAdView> adViewList) {
                // 请求成功。
                synAdView = adViewList.get(0);
                synAdView.setAdListener(new SynAdListener() {
                    @Override
                    public void onAdClose(SynAdView synAdView) {
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
            }

            @Override
            public void onAdLoadFail(SynErrorModel synErrorModel) {
                super.onAdLoadFail(synErrorModel);
                // 请求失败
            }
        });
    }


    /**
     * 显示广告
     */
    private void showAd() {
        if (synAdView == null || !synAdView.isOk()) {
            Toast.makeText(MainActivity.this, "等待加载", Toast.LENGTH_LONG).show();
            return;
        }
        // show的参数在 插屏，激励视频可以为null 。其他类型必须传入，为展示广告的容器
        synAdView.show(fl_container);
    }
}


