package com.example.xpx.weexwidgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xpx.weexwidgets.weex.WeexFragment;

public class MainActivity extends AppCompatActivity {

    private WeexFragment weexFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String remoteJs = "http://192.168.0.102:8081/dist/src/index.weex.js?name=zhangsan";
        //String localJs = "tabs.js";
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, weexFragment = WeexFragment.newInstance(remoteJs))
                .commitAllowingStateLoss();
    }

}
