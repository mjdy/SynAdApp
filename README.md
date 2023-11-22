# SYN广告SDK

## 集成
  ```
        maven { url "https://jitpack.io" }
        maven { url 'https://maven.7nc.top/repository/mjdy/' }
  ```
  
 ### 依赖
 
 ```
 implementation 'com.syn.ad:sdk:1.0.3'
 ```

## 支持的广告类型
1. 开屏
2. banner
3. 插屏
4. 信息流

## 初始化

application里调用

```
        SynSdkConfig synSdkConfig = new SynSdkConfig();
        SynAd.init(this, "123456", synSdkConfig);
```

## 加载广告




```
        SynAdConfig synAdConfig = new SynAdConfig(activity, "1", SynAdType.FEED);
        synAdConfig.setAdCount(1);
        SynAd.loadAd(synAdConfig, new SynAdLoadListener() {
            @Override
            public void onAdLoadSuccess(List<SynAdView> adViewList) {
                synAdView = adViewList.get(0);
            }

            @Override
            public void onAdLoadFail(SynErrorModel synErrorModel) {
                super.onAdLoadFail(synErrorModel);
            }
        });

```

### SynAdConfig 广告请求配置


   字段   | 是否必须|说明 | 备注
---| --- | --- | ---
context| 是 | 最好传activity | 
posId | 是| 广告位ID| 测试ID：1
adType | 是| 广告位类型 | 开屏、插屏等
adCount | 否 | 请求数量 | 默认为1 。最大为3
width | 否 | 广告宽度 | 
shakeAble|否| 是否支持摇一摇 | 仅开屏生效，默认为true

### SynAdType 广告请求类型

建议使用别名，自动补全

   ID   |别名 | 说明
---| --- | ---
1| SynAdType.SPLASH | 开屏
2 | SynAdType.INTERSTITIAL|插屏
3 | SynAdType.BANNER| banner
4 | SynAdType.FEED | 信息流

## 显示广告

```
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
                
  synAdView.show(fl_container);
```

> synAdView.show(viewContainer) 参数为显示广告的容器。插屏、激励视频可传null

# 更改记录
## 1.0.3
1. 开屏支持摇一摇

## 1.0.2
1. 拆分加载接口回调
2. 完善文档

## 1.0.1
1. 初始化项目