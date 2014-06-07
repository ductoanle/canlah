package com.viki.geohackathon.api;

import android.content.Context;

import com.android.volley.Response;
import com.ethan.libs.network.VolleyManager;
import com.ethan.libs.utils.Log;

/**
 * Created by ductoanle on 11/8/13.
 */
public abstract class BaseApi {
  private static final String TAG = "BaseApi";

  public static void makeRequest(Context context, BaseQuery query, Response.Listener<String> responseListener, Response.ErrorListener errorListener) throws Exception{
    Log.i(TAG, "Query url " + query.getRequestUrl());
    VolleyManager.makeVolleyStringRequest(context, query.getRequestMethod(), query.getRequestUrl(), query.getHeaders(), query.getParameters(), query.getPostText(), responseListener, errorListener);
  }
}
