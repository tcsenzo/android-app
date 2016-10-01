package com.senzo.qettal;

import com.senzo.qettal.events.EventFragment;
import com.senzo.qettal.history.HistoryFragment;
import com.senzo.qettal.login.LoginFragment;
import com.senzo.qettal.webview.WebViewConfigurer;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by leonardo on 16/10/01.
 */
@Singleton
@Component(modules = QettalModule.class)
public interface QettalComponent {
    void inject(LoginFragment loginFragment);

    void inject(EventFragment eventFragment);

    void inject(QettalCookieManager cookieManager);

    void inject(MainActivity mainActivity);

    void inject(QettalConfiguration qettalConfiguration);

    void inject(WebViewConfigurer webViewConfigurer);

    void inject(HistoryFragment historyFragment);
}
