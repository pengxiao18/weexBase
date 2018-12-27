package com.example.xpx.weexwidgets.weex;

import android.content.Context;
import android.view.ViewGroup;

import com.example.xpx.weexwidgets.weex.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

public class WeexEntity {

    Context context;
    IRenderListener renderListener;
    String renderJs;
    Map<String, Object> params;
    ViewGroup targetView;

    public WeexEntity(Context context, IRenderListener renderListener, String renderJs, ViewGroup targetView) {
        this.context = context;
        this.renderListener = renderListener;
        this.renderJs = renderJs;
        this.targetView = targetView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IRenderListener getRenderListener() {
        return renderListener;
    }

    public void setRenderListener(IRenderListener renderListener) {
        this.renderListener = renderListener;
    }

    public String getRenderJs() {
        return renderJs;
    }

    public void setRenderJs(String renderJs) {
        this.renderJs = renderJs;
    }

    public ViewGroup getTargetView() {
        return targetView;
    }

    public void setTargetView(ViewGroup targetView) {
        this.targetView = targetView;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> urlParams = UrlUtil.getUrlParams(renderJs);
        if (params != null && !params.isEmpty()) {
            urlParams.putAll(params);
        }
        return urlParams;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
