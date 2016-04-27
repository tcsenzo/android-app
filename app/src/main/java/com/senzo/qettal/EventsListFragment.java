package com.senzo.qettal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
            return restTemplate.getForObject("http://10.0.2.2:8080/events", EventList.class);
        }

        @Override
        protected void onPostExecute(EventList eventList) {
            ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(getActivity(), android.R.layout.simple_list_item_1, eventList.getEvents().toArray(new Event[]{}));
            eventsList.setAdapter(adapter);
        }

    }
}
