package com.example.xpx.weexwidgets.weex.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.xpx.weexwidgets.weex.WeexAgency;
import com.example.xpx.weexwidgets.weex.WeexEntity;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.Map;

public class WXFragment extends WXComponent<FrameLayout> {

    public static final String NAME = "fragment";
    private static final String JS_PATH = "jsPath";

    private WeexEntity weexEntity = null;
    private WeexAgency weexAgency = null;
    private String jsPath;
    private Map<String, Object> params;
    private boolean jsRedirect = false;
    private boolean isVisible = false;
    private boolean createAgencyOnInit = false;

    public WXFragment(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    public WXFragment(WXSDKInstance instance, WXVContainer parent, int type, BasicComponentData basicComponentData) {
        super(instance, parent, type, basicComponentData);
    }

    private void initWeexAgency(Context context, ViewGroup container) {
        weexEntity = new WeexEntity(context, null, null, container);
        weexAgency = new WeexAgency(weexEntity);
    }

    private void handleRender() {
        if (TextUtils.isEmpty(jsPath) || !isVisible) {
            return;
        }
        if (!jsRedirect) {
            // 一次渲染
            if (weexAgency == null) {
                initWeexAgency(getContext(), getHostView());
            }
            if (!weexAgency.isRendered()) {
                weexEntity.setRenderJs(jsPath);
                weexEntity.setParams(params);
                weexAgency.render();
            }
        } else {
            // 可多次渲染
            String oldJs = weexEntity != null ? weexEntity.getRenderJs() : "";
            if (TextUtils.equals(jsPath, oldJs)) {
                return;
            }
            if (weexAgency != null) {
                weexAgency.onDestroy();
                weexAgency = null;
                weexEntity = null;
            }
            initWeexAgency(getContext(), getHostView());
            weexEntity.setRenderJs(jsPath);
            weexEntity.setParams(params);
            weexAgency.render();
        }
    }

    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context) {
        FrameLayout container = new FrameLayout(context);
        if (createAgencyOnInit) {
            initWeexAgency(context, container);
        }
        return container;
    }

    @WXComponentProp(name = "router")
    public void router(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return;
        }
        Object jsPathObject = params.remove(JS_PATH);
        if (jsPathObject == null) {
            return;
        }
        this.params = params;
        this.jsPath = String.valueOf(jsPathObject);
        handleRender();
    }

    @WXComponentProp(name = "jsRedirect")
    public void jsRedirect(boolean jsRedirect) {
        this.jsRedirect = jsRedirect;
    }

    @WXComponentProp(name = "visible")
    public void visible(boolean visible) {
        this.isVisible = visible;
        if (visible) {
            if (weexAgency != null) {
                weexAgency.onResume();
            }
            handleRender();
        } else {
            if (weexAgency != null) {
                weexAgency.onPause();
            }
        }
    }

    @Override
    public void onActivityResume() {
        if (weexAgency != null) {
            weexAgency.onResume();
        }
    }

    @Override
    public void onActivityPause() {
        if (weexAgency != null) {
            weexAgency.onPause();
        }
    }

    @Override
    public void onActivityStop() {
        if (weexAgency != null) {
            weexAgency.onStop();
        }
    }

    @Override
    public void onActivityDestroy() {
        if (weexAgency != null) {
            weexAgency.onDestroy();
        }
    }
}
