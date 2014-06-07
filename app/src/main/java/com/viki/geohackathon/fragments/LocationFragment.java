package com.viki.geohackathon.fragments;


import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viki.geohackathon.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LocationFragment extends Fragment implements GoogleMap.OnMapLoadedCallback {

  private static final String TAG = "LocationFragment";

  private MapView map;
  private GoogleMap googleMap;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_location, container, false);
    map = (MapView)root.findViewById(R.id.map);
    map.onCreate(savedInstanceState);
    googleMap = map.getMap();
    googleMap.setMyLocationEnabled(true);
    googleMap.setOnMapLoadedCallback(this);
    return root;
  }

  @Override
  public void onMapLoaded() {
    animateMarker(new LatLng(1.2829005, 103.8436205), "China Town", "Chinese Chess Sunday Morning");
    animateMarker(new LatLng(1.295382, 103.8475), "Fort Canning", "Sun Chaser Photography");
  }

  private void animateMarker(final LatLng target, final String title, final String snippet){
    Projection proj = googleMap.getProjection();

    final long start = SystemClock.uptimeMillis();
    final long duration = 500;
    final Handler handler = new Handler();

    Point startPoint = proj.toScreenLocation(target);
    startPoint.y = 0;
    final LatLng startLatLng = proj.fromScreenLocation(startPoint);
    final Marker marker = googleMap.addMarker(new MarkerOptions().position(startLatLng).title(title).snippet(snippet));
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
}
