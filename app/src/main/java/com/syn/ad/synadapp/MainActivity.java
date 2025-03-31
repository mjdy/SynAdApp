package com.syn.ad.synadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.syn.ad.sdk.SynAd;
import com.syn.ad.sdk.listener.SynAdListener;
import com.syn.ad.sdk.listener.SynAdLoadListener;
import com.syn.ad.sdk.model.SynAdConfig;
import com.syn.ad.sdk.model.SynAdRenderModel;
import com.syn.ad.sdk.model.SynAdType;
import com.syn.ad.sdk.model.SynClickModel;
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
        SynAdConfig synAdConfig = new SynAdConfig(this, "3", SynAdType.DRAW); // 这三个字段必填
        SynAd.loadAd(synAdConfig, new SynAdLoadListener() {
            @Override
            public void onAdLoadSuccess(List<SynAdView> adViewList) {
                // 请求成功。
                synAdView = adViewList.get(0);
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

        // 获取物料model
        SynAdRenderModel renderModel = synAdView.getAdData();
        if (renderModel == null) {
            Toast.makeText(MainActivity.this, "物料获取异常", Toast.LENGTH_LONG).show();
            return;
        }

        AdLogUtil.log(renderModel.toString());

        fl_container.removeAllViews();
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(400, 800));
        Glide.with(this).load(renderModel.imgUrl).into(imageView);

        // 点击用的
        SynClickModel synClickModel = new SynClickModel();
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    synClickModel.down_X = event.getX();
                    synClickModel.down_Y = event.getY();
                    synClickModel.down_X_raw = event.getRawX();
                    synClickModel.down_Y_raw = event.getRawY();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    synClickModel.up_X = event.getX();
                    synClickModel.up_Y = event.getY();
                    synClickModel.up_X_raw = event.getRawX();
                    synClickModel.up_Y_raw = event.getRawY();
                }
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 点击时要执行。务必
                synAdView.callbackClick(synClickModel);
            }
        });
        fl_container.addView(imageView);


        // TODO 曝光时要执行. 务必
        synAdView.callbackShow();


    }
}


