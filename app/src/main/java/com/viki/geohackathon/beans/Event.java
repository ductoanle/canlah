package com.viki.geohackathon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ductoanle on 7/6/14.
 */
public class Event implements Parcelable{

  private static final String TAG = "Event";

  private int id;
  private String activity;
  private String privacy;
  private Date startTime;
  private Date endTime;
  private String address;
  private double latitude;
  private double longtitude;
  private String description;
  private int n_people;
  private String hostName;

  public Event(int id, String activity, String privacy, Date startTime, Date endTime, String address, double latitude, double longtitude, String description, int n_people, String hostName) {
    this.id = id;
    this.activity = activity;
    this.privacy = privacy;
    this.startTime = startTime;
    this.endTime = endTime;
    this.address = address;
    this.latitude = latitude;
    this.longtitude = longtitude;
    this.description = description;
    this.n_people = n_people;
    this.hostName = hostName;
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

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int i) {
    out.writeInt(id);
    out.writeString(activity);
    out.writeString(privacy);
    out.writeSerializable(startTime);
    out.writeSerializable(endTime);
    out.writeString(address);
    out.writeDouble(latitude);
    out.writeDouble(longtitude);
    out.writeString(description);
    out.writeInt(n_people);
    out.writeString(hostName);
  }

  public void readFromParcel(Parcel in){
    id = in.readInt();
    activity = in.readString();
    privacy = in.readString();
    startTime = (Date) in.readSerializable();
    endTime = (Date) in.readSerializable();
    address = in.readString();
    latitude = in.readDouble();
    longtitude = in.readDouble();
    description = in.readString();
    n_people = in.readInt();
    hostName = in.readString();
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
