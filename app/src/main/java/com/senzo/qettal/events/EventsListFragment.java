package com.senzo.qettal.events;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.senzo.qettal.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsListFragment extends Fragment {

    @BindView(R.id.events_list)
    ListView eventsList;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        ButterKnife.bind(this, view);
        new HttpRequestTask().execute();
        return view;
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, EventList> {
        @Override
        protected EventList doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            EventList list = restTemplate.getForObject("http://events.qettal.com/events", EventList.class);
            return list;
        }

        @Override
        protected void onPostExecute(EventList eventList) {
            EventsAdapter adapter = new EventsAdapter(getActivity(), eventList.getEvents());
            eventsList.setAdapter(adapter);
        }

    }
}
