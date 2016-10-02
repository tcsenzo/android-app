package com.senzo.qettal.webview;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.senzo.qettal.Application;
import com.senzo.qettal.QettalCookieManager;
import com.senzo.qettal.R;
import com.senzo.qettal.login.LoginFragment;

import javax.inject.Inject;

import okhttp3.Cookie;

/**
 * Created by leonardo on 16/10/01.
 */
public class WebViewConfigurer {

    @Inject
    QettalCookieManager cookies;

    public WebViewConfigurer() {
        Application.getComponent().inject(this);
    }

    public void configure(final FragmentActivity activity, WebView eventWebView) {
        WebSettings settings = eventWebView.getSettings();
        settings.setUserAgentString(
                eventWebView.getSettings().getUserAgentString()
                        + " "
                        + activity.getString(R.string.user_agent_suffix)
        );
        settings.setJavaScriptEnabled(true);


        eventWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("/login")){
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, new LoginFragment(), "login")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        Cookie cookie = cookies.getJSessionId();
        CookieManager cookieManager = CookieManager.getInstance();
        if(cookie != null){
            String cookieString = "JSESSIONID="+cookie.value();
            cookieManager.setCookie("http://www.qettal.com", cookieString);
        } else {
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
        }
    }
}
