




package com.senzo.qettal;



import android.support.v4.app.Fragment;

import com.senzo.qettal.events.EventsListFragment;
import com.senzo.qettal.history.HistoryFragment;
import com.senzo.qettal.login.LoginFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class QettalConfiguration {

    private final List<QettalFeature> unloggedFeatures = new ArrayList<>();
    private List<QettalFeature> loggedFeatures = new ArrayList<>();

    @Inject
    QettalCookieManager cookies;;

    public QettalConfiguration() {
        Application.getComponent().inject(this);

        QettalFeature homeFeature = new QettalFeature("home", R.mipmap.icon_home, R.string.main_nav_menu_item_home, new EventsListFragment());
        unloggedFeatures.add(homeFeature);
        unloggedFeatures.add(new QettalFeature("login", R.mipmap.icon_login, R.string.main_nav_menu_item_login, new LoginFragment()));
        loggedFeatures.add(homeFeature);
        loggedFeatures.add(new QettalFeature("history", R.mipmap.icon_history, R.string.main_nav_menu_item_history, new HistoryFragment()));
    }

    public List<QettalFeature> getFeatures() {
        if(cookies.getJSessionId() == null){
            return Collections.unmodifiableList(unloggedFeatures);    
        }
        return Collections.unmodifiableList(loggedFeatures);
    }


    public class QettalFeature {
        private String name;
        private int iconResId;
        private int titleResId;
        private Fragment fragment;

        public QettalFeature(final String name, final int iconResId, final int titleResId, Fragment fragment) {
            this.name = name;
            this.iconResId = iconResId;
            this.titleResId = titleResId;
            this.fragment = fragment;
        }

        public Fragment getFragment(){
            return fragment;
        }

        public String getName() {
            return name;
        }

        public int getIconResId() {
            return iconResId;
        }

        public int getTitleResId() {
            return titleResId;
        }
    }

}
