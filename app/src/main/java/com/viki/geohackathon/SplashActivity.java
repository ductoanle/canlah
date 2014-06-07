package com.viki.geohackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

  private static final int SPLASH_DISPLAY_TIME = 2000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.splash_activity);
  }

  @Override
  public void onStart() {
    super.onStart();
    new Handler().postDelayed(new Runnable() {
      public void run() {
        goToLoginActivity();
      }
    }, SPLASH_DISPLAY_TIME);
  }

  private void goToLoginActivity() {
    Intent intent = new Intent();
    intent.setClass(SplashActivity.this, LoginActivity.class);
    startActivity(intent);
    finish();
  }
}
