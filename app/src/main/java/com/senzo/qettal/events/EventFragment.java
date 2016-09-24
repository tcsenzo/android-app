package com.senzo.qettal.events;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import com.senzo.qettal.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leonardo on 16/09/23.
 */
public class EventFragment extends Fragment {

    @BindView(R.id.event_webview)
    WebView eventWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        long eventId = getArguments().getLong(EventsListFragment.EVENT_ID);
        eventWebView.getSettings().setUserAgentString(
                eventWebView.getSettings().getUserAgentString()
                        + " "
                        + getString(R.string.user_agent_suffix)
        );
        eventWebView.loadUrl("http://www.qettal.com/evento/"+ eventId);
        return view;
    }
}