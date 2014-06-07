package com.viki.geohackathon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.ethan.libs.utils.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ductoanle on 7/6/14.
 */
public class Category implements Parcelable{
  private static final String TAG = "Category";

  private static final String ID_JSON = "id";
  private static final String TITLE_JSON = "name";
  private static final String IMAGE_URL_JSON = "image";

  private int id;
  private String title;
  private String imageUrl;

  public Category(int id, String title, String image){
    this.id = id;
    this.title = title;
    this.imageUrl = image;
  }

  public Category(Parcel in){
    readFromParcel(in);
  }

  public static List<Category> fromJSONArray(JSONArray jsonArray) throws JSONException{
    List<Category> categories = new ArrayList<Category>();
    Category category;
    for (int i = 0; i < jsonArray.length(); i++){
      category = fromJSON(jsonArray.getJSONObject(i));
      if(category != null){
        Log.i(TAG, "Add category " + category.getTitle());
        categories.add(category);
      }
    }
    return categories;
  }

  public static Category fromJSON(JSONObject jsonObj){
    return new Category(jsonObj.optInt(ID_JSON), jsonObj.optString(TITLE_JSON), jsonObj.optString(IMAGE_URL_JSON));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int i) {
    out.writeInt(id);
    out.writeString(title);
    out.writeString(imageUrl);
  }

  public void readFromParcel(Parcel in){
    id = in.readInt();
    title = in.readString();
    imageUrl = in.readString();
  }

  public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
    public Category createFromParcel(Parcel in) {
      return new Category(in);
    }

    public Category[] newArray(int size) {
      return new Category[size];
    }
  };
}
