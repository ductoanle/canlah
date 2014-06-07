package com.viki.geohackathon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ethan.libs.utils.Log;
import com.viki.geohackathon.EventActivity;
import com.viki.geohackathon.R;
import com.viki.geohackathon.adapters.EventItemAdapter;
import com.viki.geohackathon.api.EventApi;
import com.viki.geohackathon.beans.Event;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventsFragment extends BaseFragment{

  private static final String TAG = "EventsFragment";
  private ListView eventsListView;
  private EventItemAdapter eventItemAdapter;
  private int categoryId;

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
    loadCategoryId();
    execute();
    return root;
  }

  private void loadCategoryId(){
    categoryId = getActivity().getIntent().getIntExtra(EventActivity.CATEGORY_ID, 0);
  }

  @Override
  public void execute() {
    show(LOADING);
    eventsListView.setVisibility(View.GONE);
    try{
      Bundle params = new Bundle();
      if (categoryId != 0){
        params.putString(EventApi.Query.CATEGORY_ID_PARAM, categoryId + "");
      }
      EventApi.Query query = EventApi.getEventListQuery(getActivity(), params);
      EventApi.makeRequest(getActivity(), query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              Log.i(TAG, "Response: " + response);
              try{
                List<Event> eventList = Event.fromJSONArray(new JSONArray(response));
                if (eventList.size() > 0){
                  eventItemAdapter.clear();
                  eventItemAdapter.setNotifyOnChange(false);
                  eventItemAdapter.addAll(eventList);
                  eventItemAdapter.notifyDataSetChanged();
                  show(DONE);
                  eventsListView.setVisibility(View.VISIBLE);
                }
                else{
                  show(EMPTY);
                }
              }
              catch (Exception e){
                show(ERROR);
                Log.e(TAG, e.getMessage(), e);
              }
            }
          },
          new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Log.e(TAG, error.getMessage(), error);
            }
          });
    }
    catch(Exception e){
      Log.e(TAG, e.getMessage(), e);
      show(ERROR);
    }
  }
}
