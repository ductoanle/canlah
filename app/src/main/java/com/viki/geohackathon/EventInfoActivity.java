package com.viki.geohackathon;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ethan.libs.network.VolleyManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viki.geohackathon.beans.Event;
import com.viki.geohackathon.utils.Utils;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventInfoActivity extends Activity {
  public static final String TAG = "EventInfoActivity";
  public static final String EVENT_PARAM = "event_param";

  private ActionBar actionBar;
  private Event event;
  private NetworkImageView eventImage;
  private ImageView eventImageOpaque;
  private TextView activity;
  private TextView address;
  private TextView time;
  private TextView about;
  private MapView map;
  private GoogleMap googleMap;
  private TextView joinBtn;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    MapsInitializer.initialize(this);
    setContentView(R.layout.activity_event_info);
    setupActionBar();
    loadEvent();
    render(savedInstanceState);
  }

  private void render(Bundle savedInstanceState){
    map = (MapView)findViewById(R.id.map);
    map.onCreate(savedInstanceState);
    map.setClickable(false);
    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(event.getLatitude(),event.getLongtitude()));
    googleMap = map.getMap();
    googleMap.moveCamera(center);
    googleMap.addMarker(new MarkerOptions().position(new LatLng(event.getLatitude(), event.getLongtitude())));
    eventImage = (NetworkImageView)findViewById(R.id.event_thumb);
    eventImageOpaque = (ImageView)findViewById(R.id.event_thumb_opaque);
    activity = (TextView)findViewById(R.id.event_activity);
    address = (TextView)findViewById(R.id.event_address);
    time = (TextView)findViewById(R.id.event_time);
    about = (TextView)findViewById(R.id.event_about);
    activity.setText(event.getActivity());
    address.setText(event.getAddress());
    time.setText(event.getTime());
    about.setText(event.getDescription());
    String cover = event.getCover();
    if (cover != null && !cover.isEmpty() && cover.endsWith("jpg")){
      VolleyManager.loadImage(this, eventImage, cover, R.drawable.default_thumbnail);
    }
    else{
      eventImage.setImageDrawable(getResources().getDrawable(R.drawable.default_thumbnail));
    }
    int width = (int) Utils.getScreenWidth(this);
    int height = (int) width * 608 / 1080;
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
    eventImage.setLayoutParams(layoutParams);
    eventImageOpaque.setLayoutParams(layoutParams);
    joinBtn = (TextView)findViewById(R.id.join_button);
    joinBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        joinBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        joinBtn.setText("Joined");
      }
    });
  }

  private void setupActionBar(){
    actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowHomeEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setDisplayUseLogoEnabled(true);
    actionBar.show();
  }

  private void loadEvent(){
    if (getIntent().hasExtra(EVENT_PARAM)){
      event = getIntent().getParcelableExtra(EVENT_PARAM);
    }
    else{
      finish();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
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

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch(id){
      case R.id.action_settings:
        return true;
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
