package com.example.xpx.weexwidgets.weex;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;

public class WeexAgency implements IWXRenderListener, Handler.Callback {

    private static final int MSG_RENDER = 1;// render 消息
    private static final int TRY_RENDER_FREQUENCY = 50;// 单位：ms

    private WeexEntity weexEntity;
    private WXSDKInstance mWXSDKInstance;
    private Handler handler = new Handler(this);
    private boolean isRendered;

    public WeexAgency(WeexEntity weexEntity) {
        this.weexEntity = weexEntity;
        this.mWXSDKInstance = new WXSDKInstance(weexEntity.context);
        this.mWXSDKInstance.registerRenderListener(this);
    }

    public boolean isRendered() {
        return isRendered;
    }

    public void render() {
        if (isRendered || weexEntity == null) {
            return;
        }
        if (TextUtils.isEmpty(weexEntity.renderJs)) {
            return;
        }
        handler.removeMessages(MSG_RENDER);
        if (WXSDKEngine.isInitialized()) {
            realRender();
        } else {
            handler.sendEmptyMessageDelayed(MSG_RENDER, TRY_RENDER_FREQUENCY);
        }
    }

    public void reLoad(boolean reloadThis) {
        if (!isRendered) {
            return;
        }
        mWXSDKInstance.reloadPage(reloadThis);
    }

    private void realRender() {
        if (weexEntity.renderJs.startsWith("http")) {
            mWXSDKInstance.renderByUrl("weexTest", weexEntity.renderJs, weexEntity.getParams(), null, WXRenderStrategy.APPEND_ASYNC);
        } else {
            String template = WXFileUtils.loadAsset(weexEntity.renderJs, weexEntity.context);
            mWXSDKInstance.render("weexTest", template, weexEntity.getParams(), null, WXRenderStrategy.APPEND_ASYNC);
        }
        isRendered = true;
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, final View view) {
        if (weexEntity == null) {
            return;
        }
        if (weexEntity.targetView != null) {
            weexEntity.targetView.removeAllViews();
            weexEntity.targetView.addView(view);
        }
        if (weexEntity.renderListener != null) {
            weexEntity.renderListener.onRenderOk(view);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        if (weexEntity == null) {
            return;
        }
        if (weexEntity.renderListener != null) {
            weexEntity.renderListener.onException(errCode, msg);
        }
    }

    public void onResume() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    public void onPause() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    public void onStop() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    public void onDestroy() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_RENDER:
                // 没有初始化成功，再次尝试render
                render();
                break;
        }
        return false;
    }

}
