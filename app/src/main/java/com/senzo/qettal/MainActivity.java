//
// Copyright 2016 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.6
//
package com.senzo.qettal;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.senzo.amazonaws.mobile.AWSMobileClient;
import com.senzo.qettal.navigation.NavigationDrawer;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private NavigationDrawer navigationDrawer;
    @Inject
    QettalConfiguration config;
    @Inject
    QettalCookieManager cookies;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getComponent().inject(this);

        AWSMobileClient.initializeMobileClientIfNecessary(this);
        setContentView(R.layout.activity_main);
        setupNavigationMenu();

        if (savedInstanceState == null) {
            navigationDrawer.showHome();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushListenerService.ACTION_SNS_NOTIFICATION));

        final ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View view) {
    }

    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.d(LOG_TAG, "Received notification from local broadcast. Display it in a dialog.");
                String defaultData = intent.getStringExtra(PushListenerService.INTENT_SNS_NOTIFICATION_DATA);
                PushNotificationData data = new ObjectMapper().readValue(defaultData, PushNotificationData.class);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(data.getTitle())
                        .setMessage(data.getMessage())
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Couldn't show alert after broadcasting push notification", e);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }

    private void setupNavigationMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView drawerItems = (ListView) findViewById(R.id.nav_drawer_items);

        navigationDrawer = new NavigationDrawer(this, toolbar, drawerLayout, drawerItems, R.id.main_fragment_container, cookies, config);
        navigationDrawer.replaceFeatures();
    }

    public NavigationDrawer getNavigation() {
        return navigationDrawer;
    }
}
