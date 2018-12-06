package com.sdr.patrol.app;

import android.app.Application;

import com.sdr.lib.SDRLibrary;
import com.sdr.patrol.BuildConfig;
import com.sdr.patrol.base.BaseConfig;
import com.sdr.patrollib.PatrolLibrary;

/**
 * Created by HyFun on 2018/12/06.
 * Email: 775183940@qq.com
 * Description:
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDRLibrary.getInstance().init(this, BuildConfig.DEBUG);
        PatrolLibrary.getInstance().init(this, "http://58.240.174.254:9022/hc_patrol/", BaseConfig.onHeaderBarRes(), BaseConfig.onHeaderBarDrawable(this));
    }
}
