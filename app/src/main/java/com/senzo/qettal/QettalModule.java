package com.senzo.qettal;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.senzo.qettal.webview.WebViewConfigurer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

/**
 * Created by leonardo on 16/10/01.
 */
@Module
public class QettalModule {

    @Provides
    @Singleton
    Context getContext(){
        return Application.getApplication();
    }

    @Provides
    @Singleton
    OkHttpClient getClient(QettalCookieManager cookiePersistor){
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), cookiePersistor);
        return new OkHttpClient.Builder().cookieJar(cookieJar).build();
    }

    @Provides
    @Singleton
    QettalConfiguration getConfig(){
        return new QettalConfiguration();
    }

    @Provides
    @Singleton
    WebViewConfigurer getWebViewConfigurer(){
        return new WebViewConfigurer();
    }
}
