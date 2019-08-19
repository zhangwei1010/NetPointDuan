package com.net.point;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.net.point.utils.ConstantUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.ref.WeakReference;

public class NetPointApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        reference = new WeakReference<>(this);
        //异常捕获
        CrashHandler.getInstance().init(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);


        //初始化友盟统计
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        /*
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        //微信
        PlatformConfig.setWeixin(ConstantUtil.WX_APP_ID, ConstantUtil.WX_APP_KEY);
        //新浪微博(第三个参数为回调地址)
//        PlatformConfig.setSinaWeibo("3111100954", "04b48b094faeb16683c32111124ebdad",
//                "http://sns.whalecloud.com/sina2/callback");
        //qq开放平台  APP ID  APP KEY
        PlatformConfig.setQQZone(ConstantUtil.QQ_APP_ID, ConstantUtil.QQ_APP_KEY);
    }

    private static WeakReference<Context> reference = null;

    public static Context getInstance() {
        return reference.get();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
