package com.viki.geohackathon.fragments;

/**
 * Created by ductoanle on 7/6/14.
 */
public interface BaseFragmentView {
  int LOADING = 0;
  int ERROR = 1;
  int DONE = 2;
  int EMPTY = 3;
  public void show(int status);
  public void execute();
}


