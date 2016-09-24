package com.senzo.qettal.events;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.senzo.qettal.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsListFragment extends Fragment {

    static final String EVENT_ID = "eventId";
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
        protected void onPostExecute(final EventList eventList) {
            EventsAdapter adapter = new EventsAdapter(getActivity(), eventList.getEvents());
            eventsList.setAdapter(adapter);
            eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Event event = eventList.getEvents().get(position);
                    EventFragment eventFragment = new EventFragment();
                    Bundle args = new Bundle();
                    args.putLong(EVENT_ID, event.getId());
                    eventFragment.setArguments(args);

                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, eventFragment, event.getName())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }
}
