package com.senzo.qettal.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.senzo.qettal.Application;
import com.senzo.qettal.R;
import com.senzo.qettal.webview.WebViewConfigurer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leonardo on 16/10/01.
 */
public class HistoryFragment extends Fragment {

    @BindView(R.id.base_webview)
    WebView eventWebView;
    @Inject
    WebViewConfigurer configurer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, view);
        Application.getComponent().inject(this);
        configurer.configure(getActivity(), eventWebView);
        eventWebView.loadUrl("http://www.qettal.com/historico");
        return view;
    }

}
