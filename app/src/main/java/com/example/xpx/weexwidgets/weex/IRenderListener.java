package com.example.xpx.weexwidgets.weex;

import android.view.View;

public interface IRenderListener {

    public void onRenderOk(View view);

    public void onException(String errCode, String msg);

}
