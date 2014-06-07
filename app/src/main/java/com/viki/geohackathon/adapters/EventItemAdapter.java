package com.viki.geohackathon.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.viki.geohackathon.beans.Event;

import java.util.List;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventItemAdapter extends ArrayAdapter<Event>{
  public EventItemAdapter(Context context, List<Event> eventList){
    super(context, 0, eventList);
  }


}
