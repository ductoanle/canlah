package com.viki.geohackathon.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ethan.libs.utils.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viki.geohackathon.EventActivity;
import com.viki.geohackathon.EventInfoActivity;
import com.viki.geohackathon.R;
import com.viki.geohackathon.api.EventApi;
import com.viki.geohackathon.beans.Event;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LocationFragment extends BaseFragment implements GoogleMap.OnMapLoadedCallback {

  private static final String TAG = "LocationFragment";

  private MapView map;
  private GoogleMap googleMap;
  private List<Event> eventList = new ArrayList<Event>();
  private int categoryId;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MapsInitializer.initialize(getActivity());
    loadCategoryId();
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_location, container, false);
    map = (MapView)root.findViewById(R.id.map);
    map.onCreate(savedInstanceState);
    googleMap = map.getMap();
    googleMap.setMyLocationEnabled(true);
    googleMap.setOnMapLoadedCallback(this);
    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
      @Override
      public void onInfoWindowClick(Marker marker) {
        Event event = searchEventByTitle(marker.getTitle());
        if (event != null){
          Intent intent = new Intent();
          intent.setClass(getActivity(), EventInfoActivity.class);
          intent.putExtra(EventInfoActivity.EVENT_PARAM, event);
          startActivity(intent);
        }
      }
    });
    return root;
  }

  private Event searchEventByTitle(String title){
    for (int i = 0; i < eventList.size(); i++){
      if (eventList.get(i).getActivity().equals(title)){
        return eventList.get(i);
      }
    }
    return null;
  }

  private void loadCategoryId(){
    if (getActivity().getIntent().hasExtra(EventActivity.CATEGORY_ID)){
      categoryId = getActivity().getIntent().getIntExtra(EventActivity.CATEGORY_ID, 0);
    }
  }

  @Override
  public void onMapLoaded() {
    execute();
  }

  @Override
  public void execute() {
    try{
      Bundle params = new Bundle();
      if (categoryId > 0){
        params.putString(EventApi.Query.CATEGORY_ID_PARAM, categoryId + "");
      }
      EventApi.Query query = EventApi.getEventListQuery(getActivity(), params);

      EventApi.makeRequest(getActivity(), query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              try{
                eventList = Event.fromJSONArray(new JSONArray(response));
                Event event;
                for (int i = 0; i < eventList.size(); i++){
                  event = eventList.get(i);
                  animateMarker(new LatLng(event.getLatitude(), event.getLongtitude()), event.getActivity(), event.getTime(), event.getCategoryId());
                }
              }
              catch (Exception e){
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

  private void animateMarker(final LatLng target, final String title, final String snippet, int categoryId){
    Projection proj = googleMap.getProjection();

    final long start = SystemClock.uptimeMillis();
    final long duration = 500;
    final Handler handler = new Handler();

    Point startPoint = proj.toScreenLocation(target);
    startPoint.y = 0;
    final LatLng startLatLng = proj.fromScreenLocation(startPoint);

    final Marker marker = googleMap.addMarker(new MarkerOptions().position(startLatLng).title(title).snippet(snippet).icon(getBitmapByCategory(categoryId)));
    final Interpolator interpolator = new LinearInterpolator();
    handler.post(new Runnable() {
      @Override
      public void run() {
        long elapsed = SystemClock.uptimeMillis() - start;
        float t = interpolator.getInterpolation((float) elapsed / duration);
        double lng = t * target.longitude + (1 - t) * startLatLng.longitude;
        double lat = t * target.latitude + (1 - t) * startLatLng.latitude;
        LatLng currLatLng = new LatLng(lat, lng);
        if (t < 1.0) {
          // Post again 10ms later.
          marker.setPosition(currLatLng);
          handler.postDelayed(this, 10);
        } else {
          // animation ended
          marker.setPosition(target);
          marker.showInfoWindow();
        }
      }
    });
  }

  @Override
  public void onResume(){
    super.onResume();
    map.onResume();
  }

  @Override
  public void onPause(){
    super.onPause();
    map.onPause();
  }

  @Override
  public void onDestroy(){
    super.onDestroy();
    map.onDestroy();
  }

  @Override
  public void onLowMemory(){
    super.onLowMemory();
    map.onLowMemory();
  }

  private BitmapDescriptor getBitmapByCategory(int categoryId){
    int drawable;
    switch (categoryId){
      case 4:
        drawable = R.drawable.art_icon;
        break;
      case 6:
        drawable = R.drawable.food_icon;
        break;
      case 16:
        drawable = R.drawable.tech_icon;
        break;
      default:
        drawable = R.drawable.learning_icon;
        break;
    }
    return BitmapDescriptorFactory.fromResource(drawable);
  }
}
