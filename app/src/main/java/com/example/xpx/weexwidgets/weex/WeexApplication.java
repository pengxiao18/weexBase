package com.example.xpx.weexwidgets.weex;

import android.app.Application;

import com.example.xpx.weexwidgets.weex.adapter.WeexImageAdapter;
import com.example.xpx.weexwidgets.weex.component.WXFragment;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class WeexApplication {

    private static WeexApplication intance;

    public static void weexInit(Application application) {
        if (intance == null) {
            intance = new WeexApplication();
        }
        intance.init(application);
    }

    public static Application getApplication() {
        if (intance == null) {
            return null;
        }
        return intance.application;
    }

    private Application application;

    private WeexApplication() {
    }

    private void init(Application application) {
        this.application = application;
        //initDebugEnvironment(true, false, "DEBUG_SERVER_HOST");
        // WXBridgeManager.updateGlobalConfig("wson_on");
        WXEnvironment.setOpenDebugLog(true);
        WXEnvironment.setApkDebugable(true);
        WXSDKEngine.addCustomOptions("weexTest", "WXSample");

        InitConfig config = new InitConfig.Builder()
                .setImgAdapter(new WeexImageAdapter())
                .build();
        WXSDKEngine.initialize(application, config);

        try {
            WXSDKEngine.registerComponent(WXFragment.NAME, WXFragment.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    private void initDebugEnvironment(boolean connectable, boolean debuggable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugServerConnectable = connectable;
            WXEnvironment.sRemoteDebugMode = debuggable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }

}
