package com.senzo.qettal.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.senzo.qettal.QettalConfiguration;
import com.senzo.qettal.R;
import com.senzo.qettal.events.EventsListFragment;

public class NavigationDrawer {
    private AppCompatActivity containingActivity;

    /* The navigation drawer layout view control. */
    private DrawerLayout drawerLayout;

    /** The view group that will contain the navigation drawer menu items. */
    private ListView drawerItems;
    private ArrayAdapter<QettalConfiguration.QettalFeature> adapter;

    /** The id of the fragment container. */
    private int fragmentContainerId;

    /**
     * Constructs the Navigation Drawer.
     * @param activity the activity that will contain this navigation drawer.
     * @param toolbar the toolbar the activity is using.
     * @param layout the DrawerLayout for this navigation drawer.
     * @param drawerItemsContainer the parent view group for the navigation drawer items.
     */
    public NavigationDrawer(final AppCompatActivity activity,
                            final Toolbar toolbar,
                            final DrawerLayout layout,
                            final ListView drawerItemsContainer,
                            final int fragmentContainerId) {
        // Keep a reference to the activity containing this navigation drawer.
        this.containingActivity = activity;
        this.drawerItems = drawerItemsContainer;
        adapter = new ArrayAdapter<QettalConfiguration.QettalFeature>(activity, com.senzo.qettal.R.layout.nav_drawer_item) {
            @Override
            public View getView(final int position, final View convertView,
                                final ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    view = activity.getLayoutInflater().inflate(com.senzo.qettal.R.layout.nav_drawer_item, parent, false);
                }
                final QettalConfiguration.QettalFeature item = getItem(position);
                ((ImageView) view.findViewById(com.senzo.qettal.R.id.drawer_item_icon)).setImageResource(item.iconResId);
                ((TextView) view.findViewById(com.senzo.qettal.R.id.drawer_item_text)).setText(item.titleResId);
                return view;
            }
        };
        drawerItems.setAdapter(adapter);
        drawerItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                QettalConfiguration.QettalFeature item = adapter.getItem(position);
                final Fragment fragment = item.getFragment();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(fragmentContainerId, fragment, item.name)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
                drawerLayout.closeDrawers();
        }
        });
        this.drawerLayout = layout;
        this.fragmentContainerId = fragmentContainerId;
;

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name) {};
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        for (QettalConfiguration.QettalFeature demoFeature : QettalConfiguration.getFeatureList()) {
            adapter.add(demoFeature);
            adapter.notifyDataSetChanged();
        }
    }

    public void showHome() {
        containingActivity.getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerId, adapter.getItem(0).getFragment(), adapter.getItem(0).name)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
