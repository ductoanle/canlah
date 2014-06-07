package com.viki.geohackathon.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ethan.libs.network.VolleyManager;
import com.viki.geohackathon.R;
import com.viki.geohackathon.beans.Event;
import com.viki.geohackathon.utils.Utils;

import java.util.List;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventItemAdapter extends ArrayAdapter<Event>{
  private static final String TAG = "EventItemAdapter";

  private List<Event> eventList;
  private LayoutInflater layoutInflater;

  public EventItemAdapter(Context context, List<Event> eventList){
    super(context, 0, eventList);
    this.eventList = eventList;
    this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (convertView == null){
      convertView = layoutInflater.inflate(R.layout.fragment_event_item, null);
      ViewHolder viewHolder = new ViewHolder(convertView);
      convertView.setTag(R.layout.fragment_event_item, viewHolder);
    }
    Event event = eventList.get(position);
    ViewHolder viewHolder = (ViewHolder) convertView.getTag(R.layout.fragment_event_item);
    String cover = event.getCover();
    if (cover != null && !cover.isEmpty() && cover.endsWith("jpg")){
      VolleyManager.loadImage(getContext(), viewHolder.eventImage, cover, R.drawable.default_thumbnail);
    }
    else{
      viewHolder.eventImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.default_thumbnail));
    }

    int width = (int)Utils.getScreenWidth((Activity) getContext());
    int height = (int) width * 608 / 1080;
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
    viewHolder.eventImage.setLayoutParams(layoutParams);
    viewHolder.eventImageOpaque.setLayoutParams(layoutParams);
    viewHolder.activity.setText(event.getActivity());
    viewHolder.address.setText(event.getAddress());
    viewHolder.time.setText(event.getTime());
    return convertView;
  }

  public static class ViewHolder{
    NetworkImageView eventImage;
    ImageView eventImageOpaque;
    TextView activity;
    TextView address;
    TextView time;

    public ViewHolder(View root){
      eventImage = (NetworkImageView)root.findViewById(R.id.event_thumb);
      eventImageOpaque = (ImageView)root.findViewById(R.id.event_thumb_opaque);
      activity = (TextView)root.findViewById(R.id.event_activity);
      address = (TextView)root.findViewById(R.id.event_address);
      time = (TextView)root.findViewById(R.id.event_time);
    }
  }
}
