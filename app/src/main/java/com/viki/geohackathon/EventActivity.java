package com.viki.geohackathon;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.viki.geohackathon.fragments.EventsFragment;
import com.viki.geohackathon.fragments.LocationFragment;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventActivity extends Activity {
  private static final String TAG = "EventActivity";

  private ActionBar actionBar;
  public static final String CATEGORY_ID = "category_id";

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupActionBar();
    addTabs();
  }

  private void setupActionBar(){
    actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowHomeEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setDisplayUseLogoEnabled(true);
    actionBar.show();
  }

  public void addTabs() {
    // Rumours
    try {
      ActionBar.Tab tab;
      tab = actionBar
          .newTab()
          .setText(R.string.tab_events)
          .setTabListener(
              new TabListener<EventsFragment>(getString(R.string.tab_events), EventsFragment.class));
      actionBar.addTab(tab);

      tab = actionBar
          .newTab()
          .setText(R.string.tab_location)
          .setTabListener(
              new TabListener<LocationFragment>(getString(R.string.tab_location), LocationFragment.class));
      actionBar.addTab(tab);
    } catch (Exception e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }

  //////////Inner classes //////////

  public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private final String mTag;
    private final Class<T> mClass;
    private final Bundle mExtras;
    private Fragment mFragment;

    /**
     * Constructor used each time a new tab is created.
     *
     * @param extras The host Activity, used to instantiate the fragment
     * @param tag      The identifier tag for the fragment
     * @param clz      The fragment's Class, used to instantiate the fragment
     */
    public TabListener(
        String tag,
        Class<T> clz,
        Bundle extras) {
      FragmentManager manager = getFragmentManager();

      mTag = tag;
      mClass = clz;
      mExtras = extras;
      mFragment = manager.findFragmentByTag(mTag);
      if (mFragment != null && !mFragment.isDetached()) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.detach(mFragment);
        ft.commit();
      }
    }

    public TabListener(String tag, Class<T> clz) {
      this(tag, clz, null);
    }

    public Fragment getFragment() {
      FragmentManager manager = getFragmentManager();
      return manager.findFragmentByTag(mTag);
    }

    protected String getTag() {
      return mTag;
    }

    ////// ActionBar.TabListener implementation

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
      if (mFragment == null) {
        mFragment = Fragment.instantiate(EventActivity.this, mClass.getName(), mExtras);
        ft.add(R.id.fragment_main, mFragment, mTag);
      } else {
        ft.attach(mFragment);
      }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
      if (mFragment != null) {
        ft.detach(mFragment);
      }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
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
