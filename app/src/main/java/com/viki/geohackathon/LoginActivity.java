package com.viki.geohackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by ductoanle on 5/6/14.
 */
public class LoginActivity extends Activity {
  private static final String TAG = "LoginActivity";

  private ImageView loginFacebook;
  private ImageView loginTwitter;
  private ImageView loginGoogle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.login_activity);
    setupView();
  }

  private void setupView(){
    View.OnClickListener clickListener = new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    };
    loginFacebook = (ImageView)findViewById(R.id.btn_facebook);
    loginFacebook.setOnClickListener(clickListener);
    loginTwitter = (ImageView)findViewById(R.id.btn_twitter);
    loginTwitter.setOnClickListener(clickListener);
    loginGoogle = (ImageView)findViewById(R.id.btn_google);
    loginGoogle.setOnClickListener(clickListener);
  }
}
