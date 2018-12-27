package com.example.xpx.weexwidgets.weex.util;

import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UrlUtil {

    public static Map<String, Object> getUrlParams(String url) {
        Map<String, Object> params = new HashMap<>();
        if (TextUtils.isEmpty(url)) {
            return params;
        }
        Uri uri = Uri.parse(url);
        Set<String> keys = uri.getQueryParameterNames();
        if (keys == null || keys.isEmpty()) {
            return params;
        }
        for (String key : keys) {
            params.put(key, uri.getQueryParameter(key));
        }
        return params;
    }

}
