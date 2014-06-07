package com.viki.geohackathon.api;

import android.content.Context;
import android.os.Bundle;

import com.ethan.libs.network.VolleyManager;

/**
 * Created by ductoanle on 7/6/14.
 */
public class CategoryApi extends BaseApi{
  private static final String TAG = "CategoryApi";

  public static class Query extends BaseQuery{
    private static final String TAG = "CategoryApi.Query";

    private Query(Context context, int request, Bundle params, int requestMethod) throws Exception{
      super(context,request, params, requestMethod);
    }

    public static Query prepareQuery(Context context, int request, Bundle params, int requestMethod) throws Exception{
      return new Query(context, request, params, requestMethod);
    }

    public static final int GET_CATEGORY_LIST = 0;

    public static final String GET_CATEGORY_LIST_URL = SERVER_URL + CATEGORIES_END_POINT;

    @Override
    protected String getRequestUrlFromRequest(int request, Bundle params) throws Exception {
      String requestUrl = null;
      switch(request){
        case GET_CATEGORY_LIST:
          requestUrl = GET_CATEGORY_LIST_URL;
          break;
      }
      return requestUrl;
    }
  }

  public static Query getCategoriesListQuery(Context context, Bundle params) throws Exception{
    return Query.prepareQuery(context, Query.GET_CATEGORY_LIST, params, VolleyManager.METHOD_GET);
  }
}
