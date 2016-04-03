package com.mysampleapp.demo;

import android.support.v4.app.Fragment;

import com.mysampleapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QettalConfiguration {

    private static final List<QettalFeature> featureList = new ArrayList<QettalFeature>();

    static {
        addFeature("list_spetacles", R.mipmap.icon_home, R.string.main_nav_menu_item_home,
                R.string.main_menu_home_subtitle, R.string.main_menu_home_overview,
                R.string.main_menu_home_description, R.string.main_menu_home_powered_by, new HomeFragment());
    }

    public static List<QettalFeature> getFeatureList() {
        return Collections.unmodifiableList(featureList);
    }

    public static QettalFeature getFeatureByName(final String name) {
        for (QettalFeature feature : featureList) {
            if (feature.name.equals(name)) {
                return feature;
            }
        }
        return null;
    }

    private static void addFeature(final String name, final int iconResId, final int titleResId,
                                   final int subtitleResId, final int overviewResId,
                                   final int descriptionResId, final int poweredByResId, Fragment fragment) {
        QettalFeature demoFeature = new QettalFeature(name, iconResId, titleResId, subtitleResId,
                overviewResId, descriptionResId, poweredByResId, fragment);
        featureList.add(demoFeature);
    }

    public static class QettalFeature {
        public String name;
        public int iconResId;
        public int titleResId;
        public int subtitleResId;
        public int overviewResId;
        public int descriptionResId;
        public int poweredByResId;
        private Fragment fragment;
        public List<DemoItem> demos;

        public QettalFeature() {

        }

        public QettalFeature(final String name, final int iconResId, final int titleResId,
                             final int subtitleResId, final int overviewResId,
                             final int descriptionResId, final int poweredByResId, Fragment fragment) {
            this.name = name;
            this.iconResId = iconResId;
            this.titleResId = titleResId;
            this.subtitleResId = subtitleResId;
            this.overviewResId = overviewResId;
            this.descriptionResId = descriptionResId;
            this.poweredByResId = poweredByResId;
            this.fragment = fragment;
        }

        public Fragment getFragment(){
            return fragment;
        }
    }

    public static class DemoItem {
        public int titleResId;
        public int iconResId;
        public int buttonTextResId;
        public String fragmentClassName;

        public DemoItem(final int titleResId, final int iconResId, final int buttonTextResId,
                        final Class<? extends Fragment> fragmentClass) {
            this.titleResId = titleResId;
            this.iconResId = iconResId;
            this.buttonTextResId = buttonTextResId;
            this.fragmentClassName = fragmentClass.getName();
        }
    }
}
