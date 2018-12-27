package com.example.xpx.weexwidgets.weex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xpx.weexwidgets.R;

public class WeexFragment extends Fragment implements IRenderListener {

    public static WeexFragment newInstance(@NonNull String renderJs) {
        Bundle args = new Bundle();
        WeexFragment fragment = new WeexFragment();
        fragment.setArguments(args);
        fragment.weexEntity.renderJs = renderJs;
        return fragment;
    }

    private WeexEntity weexEntity = new WeexEntity(WeexApplication.getApplication(), this, null, null);
    private WeexAgency weexAgency = new WeexAgency(weexEntity);

    private View rootView;
    private ViewGroup weeView;
    private boolean isCreated, isVisible, autoRender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weex_layout, container, false);
        weeView = rootView.findViewById(R.id.weex_view);
        weexEntity.targetView = weeView;
        isCreated = true;
        // 渲染JS
        render();
        return rootView;
    }

    public void render() {
        if (TextUtils.isEmpty(weexEntity.renderJs)) {
            return;
        }
        isVisible = getUserVisibleHint();
        if (!isCreated || !isVisible) {
            return;
        }
        weexAgency.render();
    }

    public void reload(boolean reloadThis) {
        weexAgency.reLoad(reloadThis);
    }

    @Override
    public void onRenderOk(View view) {
    }

    @Override
    public void onException(String errCode, String msg) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if (weexAgency != null) {
            if (isVisibleToUser) {
                weexAgency.onResume();
            } else {
                weexAgency.onPause();
            }
        }
        render();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (weexAgency != null) {
            if (!hidden) {
                weexAgency.onResume();
            } else {
                weexAgency.onPause();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (weexAgency != null) {
            weexAgency.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (weexAgency != null) {
            weexAgency.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (weexAgency != null) {
            weexAgency.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (weexAgency != null) {
            weexAgency.onDestroy();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (weexAgency != null) {
            weexAgency.onActivityResult(requestCode, resultCode, data);
        }
    }

}
