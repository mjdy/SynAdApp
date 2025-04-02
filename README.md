# SYN广告SDK_BD

## 集成
  ```
		maven { url 'https://maven.7nc.top/repository/mjdy/' }
  ```

### 依赖

 ```
 		implementation 'cn.cusky.ad:sdk:1.0.7.bd'
 ```


## 初始化

application里调用

```
        SynSdkConfig synSdkConfig = new SynSdkConfig();
        synSdkConfig.setOaid("your oaid"); // 务必传入oaid
        SynAd.init(this, "9xf1IfFT", synSdkConfig);
```

> bd版本移除了获取oaid，所以oaid务必要传入

## 加载广告

### 同步


```

			SynAdConfig synAdConfig = new SynAdConfig( activity,"3", SynAdType.DRAW);
			synAdInstance = SynAd.loadAdSync(synAdConfig);
			
```

### 异步

```

			SynAdConfig synAdConfig = new SynAdConfig(activity, "3", SynAdType.DRAW);
			SynAd.loadAd(synAdConfig, new SynAdLoadListener() {
			    @Override
			    public void onAdLoadSuccess(List<SynAdView> adViewList) {
			         synAdInstance = adInstanceList.get(0);
			         SynAdRenderModel renderModel = synAdInstance.getAdData(); // 获取物料
			    }
			
			    @Override
			    public void onAdLoadFail(SynErrorModel synErrorModel) {
			        super.onAdLoadFail(synErrorModel);
			    }
			});
			

```

## 曝光与点击

### 广告展示时，务必调用

```
		synAdInstance.callbackShow();
```

### 绑定广告展示view，处理点击事件
```
		synAdInstance.bindView(ad_view_contianer);
```


### SynAdRenderModel 物料model
 字段  |说明 | 备注
---| --- | --- 
title| 标题 |
desc | 简介|
source | 广告源| 京东
imgUrl | 图片url|
logoUrl | logoUrl|
videoModel | 视频物料| SynAdRenderVideoModel

#### SynAdRenderVideoModel 物料视频model

   字段  |说明 | 备注
---| --- | --- 
url| 视频url |
duration| 时长 | 单位秒
cover | 封面url |

### SynAdConfig 广告请求配置


   字段   | 是否必须|说明 | 备注
---| --- | --- | ---
context| 是 | 最好传activity |
posId | 是| 广告位ID| 测试ID：3
adType | 是| 广告位类型 | draw

### SynAdType 广告请求类型

建议使用别名，自动补全

   ID   |别名 | 说明
---| --- | ---
1 | SynAdType.SPLASH | 开屏
2 | SynAdType.INTERSTITIAL|插屏
3 | SynAdType.BANNER| banner
4 | SynAdType.FEED | 信息流
5 | SynAdType.DRAW | DRAW
6 | SynAdType.VIDEO | 视频
7 | SynAdType.REWARD_VIDEO | 激励视频


# 更改记录
## 1.0.7.bd
1. 更改包名
2. 提供同步方法
3. 封装点击事件

## 1.0.6.bd
1. 提供物料

## 1.0.4
1. 升级为https
## 1.0.3
1. 开屏支持摇一摇

## 1.0.2
1. 拆分加载接口回调
2. 完善文档

## 1.0.1
1. 初始化项目