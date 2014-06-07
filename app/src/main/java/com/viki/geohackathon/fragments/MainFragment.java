package com.viki.geohackathon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ethan.libs.utils.Log;
import com.viki.geohackathon.EventActivity;
import com.viki.geohackathon.R;
import com.viki.geohackathon.adapters.CategoryItemAdapter;
import com.viki.geohackathon.api.CategoryApi;
import com.viki.geohackathon.beans.Category;
import com.viki.geohackathon.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainFragment extends BaseFragment {
  private static final String TAG = "MainFragment";

  private GridView categoriesView;
  private CategoryItemAdapter categoryItemAdapter;
  private ImageView refreshBtn;

  public MainFragment() {
  }

  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
      // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_main, container, false);
    categoriesView = (GridView)root.findViewById(R.id.categories_list);
    int measure = (Utils.getImageWidth(getActivity()));
    categoriesView.setColumnWidth(measure);
    categoriesView.setHorizontalSpacing(Utils.getImagePadding(getActivity()));
//    categoriesView.setVerticalSpacing(Utils.getImagePadding(getActivity()));

    loadingPagination = root.findViewById(R.id.loadingpagination_loading);
    loadingPaginationError = root.findViewById(R.id.loadingpagination_error);
    refreshBtn = (ImageView)root.findViewById(R.id.loadingpagination_error_refresh_btn);
    viewContainer = root.findViewById(R.id.category_empty);

    refreshBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        show(LOADING);
        execute();
      }
    });

    categoryItemAdapter = new CategoryItemAdapter(getActivity(), new ArrayList<Category>());
    categoriesView.setAdapter(categoryItemAdapter);
    categoriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), EventActivity.class);
        startActivity(intent);
      }
    });
    execute();
    return root;
  }


  @Override
  public void execute() {
    Log.i(TAG, "Running execute");
    show(LOADING);
    categoriesView.setVisibility(View.GONE);
    try{
      Bundle params = new Bundle();
      CategoryApi.Query query = CategoryApi.getCategoriesListQuery(getActivity(), params);
      CategoryApi.makeRequest(getActivity(), query, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
          Log.i(TAG, "Response: " + response);
          try{
            List<Category> categoryList = Category.fromJSONArray(new JSONArray(response));
            if (categoryList.size() > 0){
              categoryItemAdapter.clear();
              categoryItemAdapter.setNotifyOnChange(false);
              categoryItemAdapter.addAll(categoryList);
              categoryItemAdapter.notifyDataSetChanged();
              show(DONE);
              categoriesView.setVisibility(View.VISIBLE);
            }
            else{
              show(EMPTY);
            }
          }
          catch (Exception e){
            show(ERROR);
            Log.e(TAG, e.getMessage(), e);
          }
        }
      },
      new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e(TAG, error.getMessage(), error);
          }
        });
    }
    catch(Exception e){
      Log.e(TAG, e.getMessage(), e);
      show(ERROR);
    }
  }
}
