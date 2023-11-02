# SYN广告SDK

## 集成
  ```
        maven { url "https://jitpack.io" }
        maven { url 'https://maven.7nc.top/repository/mjdy/' }
  ```
  
 ### 依赖
 
 ```
 implementation 'com.syn.ad:sdk:1.0.1'
 ```

## 支持的广告类型
1. 开屏

## 初始化

application里调用

```
        SynSdkConfig synSdkConfig = new SynSdkConfig();
        SynAd.init(this, "123456", synSdkConfig);
```

## 加载广告

```
SynAdView synAdView;

        SynAdConfig synAdConfig = new SynAdConfig(context, posId, adType);

        SynAd.loadAd(synAdConfig, new SynAdListener() {
            @Override
            public void onAdLoadSuccess(List<SynAdView> adViewList) {
                synAdView = adViewList.get(0);
                
                // 显示广告
                synAdView.show(view_container);
            }


        });

```
