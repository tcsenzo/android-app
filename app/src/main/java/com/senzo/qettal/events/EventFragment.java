package com.senzo.qettal.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.senzo.qettal.Application;
import com.senzo.qettal.QettalCookieManager;
import com.senzo.qettal.LoginFragment;
import com.senzo.qettal.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cookie;

/**
 * Created by leonardo on 16/09/23.
 */
public class EventFragment extends Fragment {

    @BindView(R.id.event_webview)
    WebView eventWebView;
    @Inject
    QettalCookieManager cookies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        Application.getComponent().inject(this);
        long eventId = getArguments().getLong(EventsListFragment.EVENT_ID);
        WebSettings settings = eventWebView.getSettings();
        settings.setUserAgentString(
                eventWebView.getSettings().getUserAgentString()
                        + " "
                        + getString(R.string.user_agent_suffix)
        );
        settings.setJavaScriptEnabled(true);


        eventWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("/login")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, new LoginFragment(), "login")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        Cookie cookie = this.cookies.getJSessionId();
        if(cookie != null){
            String cookieString = "JSESSIONID="+cookie.value();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setCookie("http://www.qettal.com", cookieString);
        }
        eventWebView.loadUrl("http://www.qettal.com/evento/"+eventId);
        return view;
    }

}
