package com.viki.geohackathon.api;

/**
 * Created by ductoanle on 7/6/14.
 */

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by ductoanle on 11/8/13.
 */
public abstract class BaseQuery {

  private static final String TAG = "BaseQuery";

  public static final String SERVER_URL = "http://192.168.56.1";
  //    public static final String SERVER_URL = "http://192.168.1.109:3000";
  public static final String CATEGORIES_END_POINT = "/categories";
  public static final String EVENT_END_POINT = "/event/list";
  public static final String EVENT_ATTEND_END_POINT = "/event/attend";
  public static final String USER_END_POINT = "/users/list";

  public static final String CATEGORY_ID_PARAM = "category_id";


  private String requestUrl;
  private LinkedHashMap<String, String> parameters;
  private LinkedHashMap<String, String> headers;
  private String postText = null;
  private int requestMethod;
  private Bundle params;
  private Context context;

  public BaseQuery(Context context, int request, Bundle params, int requestMethod) throws Exception{
    this.context = context;
    this.requestUrl = getRequestUrlFromRequest(request, params);
    this.requestMethod = requestMethod;
    this.parameters = new LinkedHashMap<String, String>();
    this.headers = new LinkedHashMap<String, String>();
    this.params = params;
    this.parameters = getHttpParametersFromBundleParams();
  }

  public BaseQuery(Context context, int request, int requestMethod,  Bundle params, String postText) throws Exception{
    this(context, request, params, requestMethod);
    this.postText = postText;
  }

  protected String getRequestUrl(){
    return requestUrl;
  }

  public int getRequestMethod() {
    return requestMethod;
  }

  protected LinkedHashMap<String, String> getParameters(){
    return parameters;
  }

  protected LinkedHashMap<String, String> getHeaders(){
    return headers;
  }

  protected void putHeader(String key, String value){
    headers.put(key, value);
  }

  protected String getPostText() {
    return postText;
  }

  protected JSONObject getJSONPostText() throws JSONException {
    if (!TextUtils.isEmpty(postText)){
      return new JSONObject(postText);
    }
    else{
      return null;
    }
  }

  protected abstract String getRequestUrlFromRequest(int request, Bundle params) throws Exception;

  private LinkedHashMap<String, String> getHttpParametersFromBundleParams() throws Exception{
    String key;
    LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
    Iterator<String> iter = params.keySet().iterator();
    while(iter.hasNext()){
      key = iter.next();
      parameters.put(key, params.getString(key));
    }
    return parameters;
  }
}

