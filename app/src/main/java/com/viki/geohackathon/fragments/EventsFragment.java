package com.viki.geohackathon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.viki.geohackathon.R;
import com.viki.geohackathon.adapters.EventItemAdapter;
import com.viki.geohackathon.beans.Event;

import java.util.ArrayList;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventsFragment extends BaseFragment{

  private static final String TAG = "EventsFragment";
  private ListView eventsListView;
  private EventItemAdapter eventItemAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_events, container, false);
    eventsListView = (ListView)root.findViewById(R.id.event_list);
    loadingPagination = root.findViewById(R.id.loadingpagination_loading);
    loadingPaginationError = root.findViewById(R.id.loadingpagination_error);
    refreshBtn = (ImageView)root.findViewById(R.id.loadingpagination_error_refresh_btn);
    viewContainer = root.findViewById(R.id.category_empty);

    refreshBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        show(LOADING);
        execute();
      }
    });

    eventItemAdapter = new EventItemAdapter(getActivity(), new ArrayList<Event>());
    eventsListView.setAdapter(eventItemAdapter);
    eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

      }
    });
    execute();
    return root;
  }

  @Override
  public void execute() {

  }
}
