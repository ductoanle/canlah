package com.viki.geohackathon.api;

import android.content.Context;
import android.os.Bundle;

import com.ethan.libs.network.VolleyManager;

/**
 * Created by ductoanle on 7/6/14.
 */
public class EventApi extends BaseApi{
  private static final String TAG = "EventApi";

  public static class Query extends BaseQuery{
    private static final String TAG = "EventApi.Query";

    private Query(Context context, int request, Bundle params, int requestMethod) throws Exception{
      super(context,request, params, requestMethod);
    }

    public static Query prepareQuery(Context context, int request, Bundle params, int requestMethod) throws Exception{
      return new Query(context, request, params, requestMethod);
    }

    public static final int GET_EVENT_LIST = 0;
    public static final int CREATE_EVENT = 1;

    public static final String GET_EVENT_LIST_URL = SERVER_URL + EVENT_END_POINT;
    public static final String CREATE_EVENT_URL = SERVER_URL + EVENT_ATTEND_END_POINT;

    @Override
    protected String getRequestUrlFromRequest(int request, Bundle params) throws Exception {
      String requestUrl = null;
      switch(request){
        case GET_EVENT_LIST:
          requestUrl = GET_EVENT_LIST_URL;
          break;
        case CREATE_EVENT:
          requestUrl = CREATE_EVENT_URL;
      }
      return requestUrl;
    }
  }

  public static Query getEventListQuery(Context context, Bundle params) throws Exception{
    return Query.prepareQuery(context, Query.GET_EVENT_LIST, params, VolleyManager.METHOD_GET);
  }
}
