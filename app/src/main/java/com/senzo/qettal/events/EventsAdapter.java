package com.senzo.qettal.events;


import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.senzo.qettal.R;

import java.util.List;

public class EventsAdapter extends BaseAdapter {
    private Activity activity;
    private List<Event> events;

    public EventsAdapter(Activity activity, List<Event> events) {
        this.activity = activity;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return events.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.events_item, null);
        Event event = events.get(position);

        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        ImageView image = (ImageView) view.findViewById(R.id.image);
        imageLoader.displayImage(event.getImage()+"?i="+position, image, new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build());

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(event.getName());

        TextView originalPrice = (TextView) view.findViewById(R.id.original_price);
        originalPrice.setText(format(event.getOriginalPrice()));
        originalPrice.setPaintFlags(originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(format(event.getPrice()));

        return view;
    }

    private String format(String price) {
        return "R$ "+price.replace(".", ",");
    }
}

