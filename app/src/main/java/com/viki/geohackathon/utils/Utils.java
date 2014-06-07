package com.viki.geohackathon.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.viki.geohackathon.R;

/**
 * Created by ductoanle on 7/6/14.
 */
public class Utils {

  public static int getScreenWidth(Activity activity){
    Display display = activity.getWindowManager().getDefaultDisplay();
    DisplayMetrics outMetrics = new DisplayMetrics();
    display.getMetrics(outMetrics);
    return outMetrics.widthPixels;
  }

  public static int getImageWidth(Activity activity) {
    Display display = activity.getWindowManager().getDefaultDisplay();
    DisplayMetrics outMetrics = new DisplayMetrics();
    display.getMetrics(outMetrics);
    float density = activity.getResources().getDisplayMetrics().density;
    return (int) ((outMetrics.widthPixels - (activity.getResources().getDimension(R.dimen.padding_default) + 10) * density) / 2);
  }

  public static int getImagePadding(Activity activity) {
    Display display = activity.getWindowManager().getDefaultDisplay();
    DisplayMetrics outMetrics = new DisplayMetrics();
    display.getMetrics(outMetrics);
    float density = activity.getResources().getDisplayMetrics().density;
    return (int) ((outMetrics.widthPixels - getImageWidth(activity) * 2) - 10 * density);
  }
}
