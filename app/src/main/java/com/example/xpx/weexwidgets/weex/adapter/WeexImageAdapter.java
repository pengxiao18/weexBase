package com.example.xpx.weexwidgets.weex.adapter;

import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.xpx.weexwidgets.weex.adapter.BlurTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

public class WeexImageAdapter implements IWXImgLoaderAdapter {

    @Override
    public void setImage(final String url, final ImageView view, WXImageQuality quality, final WXImageStrategy strategy) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                if (null != strategy) {
                    recordImgLoadAction(strategy.instanceId);
                }

                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }

                if (!TextUtils.isEmpty(strategy.placeHolder)) {
                    Picasso.Builder builder = new Picasso.Builder(WXEnvironment.getApplication());
                    Picasso picasso = builder.build();
                    picasso.load(Uri.parse(strategy.placeHolder)).into(view);

                    view.setTag(strategy.placeHolder.hashCode(), picasso);
                }

                Picasso.with(WXEnvironment.getApplication())
                        .load(temp)
                        .transform(new BlurTransformation(strategy.blurRadius))
                        .into(view, new Callback() {
                            @Override
                            public void onSuccess() {
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, true, null);
                                }
                                recordImgLoadResult(strategy.instanceId, true, null);

                                if (!TextUtils.isEmpty(strategy.placeHolder)) {
                                    ((Picasso) view.getTag(strategy.placeHolder.hashCode())).cancelRequest(view);
                                }
                            }

                            @Override
                            public void onError() {
                                if (strategy.getImageListener() != null) {
                                    strategy.getImageListener().onImageFinish(url, view, false, null);
                                }
                                recordImgLoadResult(strategy.instanceId, false, null);
                            }
                        });
            }
        };
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            WXSDKManager.getInstance().postOnUiThread(runnable, 0);
        }
    }

    private void recordImgLoadAction(String instanceId) {
        WXSDKInstance instance = WXSDKManager.getInstance().getAllInstanceMap().get(instanceId);
        if (null == instance || instance.isDestroy()) {
            return;
        }
        instance.getApmForInstance().actionLoadImg();
    }

    private void recordImgLoadResult(String instanceId, boolean succeed, String errorCode) {
        WXSDKInstance instance = WXSDKManager.getInstance().getAllInstanceMap().get(instanceId);
        if (null == instance || instance.isDestroy()) {
            return;
        }
        instance.getApmForInstance().actionLoadImgResult(succeed, errorCode);
    }

}
