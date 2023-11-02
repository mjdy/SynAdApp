package com.syn.ad.synadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.syn.ad.sdk.SynAd;
import com.syn.ad.sdk.listener.SynAdListener;
import com.syn.ad.sdk.model.SynAdConfig;
import com.syn.ad.sdk.model.SynAdType;
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
        SynAdConfig synAdConfig = new SynAdConfig(this, "1", SynAdType.FEED);
        SynAd.loadAd(synAdConfig, new SynAdListener() {
            @Override
            public void onAdLoadSuccess(List<SynAdView> adViewList) {
                synAdView = adViewList.get(0);
            }

            @Override
            public void onAdDismiss() {
                super.onAdDismiss();
                AdLogUtil.log("ad dismiss");
            }

            @Override
            public void onAdSkip() {
                super.onAdSkip();
                AdLogUtil.log("ad skip");
            }

            @Override
            public void onAdClose(SynAdView mjAdView) {
                super.onAdClose(mjAdView);
                AdLogUtil.log("ad close");

                if (synAdView != null) {
                    synAdView.destroy();
                }

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
        synAdView.show(fl_container);
    }
}