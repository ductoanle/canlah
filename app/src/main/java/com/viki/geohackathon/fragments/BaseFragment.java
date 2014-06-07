package com.viki.geohackathon.fragments;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ductoanle on 7/6/14.
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentView{
  private static final String TAG = "BaseFragment";

  protected View loadingPagination;
  protected View loadingPaginationError;
  protected View viewContainer;
  protected ImageView refreshBtn;

  @Override
  public void show(int status) {
    switch(status){
      case LOADING:
        loadingPagination.setVisibility(View.VISIBLE);
        loadingPaginationError.setVisibility(View.GONE);
        break;
      case ERROR:
        loadingPagination.setVisibility(View.GONE);
        loadingPaginationError.setVisibility(View.VISIBLE);
        break;
      case DONE:
        loadingPagination.setVisibility(View.GONE);
        loadingPaginationError.setVisibility(View.GONE);
        break;
      case EMPTY:
        if (viewContainer != null){
          viewContainer.setVisibility(View.GONE);
        }
        break;
    }
  }
}
