package com.example.xpx.weexwidgets;

import android.app.Application;

import com.example.xpx.weexwidgets.weex.WeexApplication;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化weex sdk
        WeexApplication.weexInit(this);
    }

}
