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
public class Event implements Parcelable{

  private static final String TAG = "Event";

  private static final String ID_JSON = "id";
  private static final String DESCRIPTION_JSON = "description";
  private static final String PRIVACY_JSON = "privacy";
  private static final String ACTIVITY_JSON = "activity";
  private static final String TAGS_JSON = "tags";
  private static final String TIME_JSON = "time";
  private static final String ADDRESS_JSON = "address";
  private static final String LATITUDE_JSON = "latitude";
  private static final String LONGTITUDE_JSON = "longtitude";
  private static final String N_PEOPLE_JSON = "n_people";
  private static final String HOST_NAME_JSON = "hostName";
  private static final String COVER_JSON = "cover";

  private int id;
  private String description;
  private String privacy;
  private String activity;
  private String tags;
  private String time;
  private String address;
  private double latitude;
  private double longtitude;
  private int n_people;
  private String hostName;
  private String cover;

  public Event(int id, String description, String privacy, String activity, String tags, String time, String address, double latitude, double longtitude, int n_people, String hostName, String cover) {
    this.id = id;
    this.description = description;
    this.privacy = privacy;
    this.activity = activity;
    this.tags = tags;
    this.time = time;
    this.address = address;
    this.latitude = latitude;
    this.longtitude = longtitude;
    this.n_people = n_people;
    this.hostName = hostName;
    this.cover = cover;
  }

  public static List<Event> fromJSONArray(JSONArray jsonArray) throws JSONException {
    List<Event> events = new ArrayList<Event>();
    Event event;
    for (int i = 0; i < jsonArray.length(); i++){
      event = fromJSON(jsonArray.getJSONObject(i));
      if(event != null){
        Log.i(TAG, "Add event " + event.getActivity());
        events.add(event);
      }
    }
    return events;
  }

  public static Event fromJSON(JSONObject jsonObj){
    return new Event(jsonObj.optInt(ID_JSON), jsonObj.optString(DESCRIPTION_JSON),
                     jsonObj.optString(PRIVACY_JSON), jsonObj.optString(ACTIVITY_JSON),
                     jsonObj.optString(TAGS_JSON), jsonObj.optString(TIME_JSON),
                     jsonObj.optString(ADDRESS_JSON), jsonObj.optDouble(LATITUDE_JSON),
                     jsonObj.optDouble(LATITUDE_JSON), jsonObj.optInt(N_PEOPLE_JSON),
                     jsonObj.optString(HOST_NAME_JSON), jsonObj.optString(COVER_JSON));
  }


  public Event(Parcel in){
    readFromParcel(in);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getActivity() {
    return activity;
  }

  public void setActivity(String activity) {
    this.activity = activity;
  }

  public String getPrivacy() {
    return privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String endTime) {
    this.tags = tags;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongtitude() {
    return longtitude;
  }

  public void setLongtitude(double longtitude) {
    this.longtitude = longtitude;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getN_people() {
    return n_people;
  }

  public void setN_people(int n_people) {
    this.n_people = n_people;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int i) {
    out.writeInt(id);
    out.writeString(activity);
    out.writeString(privacy);
    out.writeString(time);
    out.writeString(tags);
    out.writeString(address);
    out.writeDouble(latitude);
    out.writeDouble(longtitude);
    out.writeString(description);
    out.writeInt(n_people);
    out.writeString(hostName);
    out.writeString(cover);
  }

  public void readFromParcel(Parcel in){
    id = in.readInt();
    activity = in.readString();
    privacy = in.readString();
    time = in.readString();
    tags = in.readString();
    address = in.readString();
    latitude = in.readDouble();
    longtitude = in.readDouble();
    description = in.readString();
    n_people = in.readInt();
    hostName = in.readString();
    cover = in.readString();
  }

  public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
    public Event createFromParcel(Parcel in) {
      return new Event(in);
    }

    public Event[] newArray(int size) {
      return new Event[size];
    }
  };
}
