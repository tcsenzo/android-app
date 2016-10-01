package com.senzo.qettal;

import com.senzo.qettal.events.EventFragment;

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
}
