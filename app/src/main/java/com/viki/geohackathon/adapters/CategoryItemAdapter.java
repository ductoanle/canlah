package com.viki.geohackathon.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ethan.libs.network.VolleyManager;
import com.viki.geohackathon.R;
import com.viki.geohackathon.beans.Category;
import com.viki.geohackathon.utils.Utils;

import java.util.List;

/**
 * Created by ductoanle on 7/6/14.
 */
public class CategoryItemAdapter extends ArrayAdapter<Category> {

  private static final String TAG = "CategoryItemAdapter";

  private List<Category> categoryList;
  private LayoutInflater layoutInflater;

  public CategoryItemAdapter(Context context, List<Category> categoriesList) {
    super(context, 0, categoriesList);
    this.categoryList = categoriesList;
    this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (convertView == null){
      convertView = layoutInflater.inflate(R.layout.category_item, null);
      ViewHolder viewHolder = new ViewHolder(convertView);
      convertView.setTag(R.layout.category_item, viewHolder);
    }
    Category category = categoryList.get(position);
    ViewHolder viewHolder = (ViewHolder) convertView.getTag(R.layout.category_item);
    int measure = (int) (Utils.getImageWidth((Activity)getContext()));
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(measure, measure);
    viewHolder.categoryImage.setLayoutParams(layoutParams);
    viewHolder.categoryImageOpaque.setLayoutParams(layoutParams);
    VolleyManager.loadImage(getContext(), viewHolder.categoryImage, category.getImageUrl(), android.R.color.black);
    viewHolder.title.setText(category.getTitle());
    return convertView;
  }

  public static class ViewHolder{
    NetworkImageView categoryImage;
    ImageView categoryImageOpaque;
    TextView title;

    public ViewHolder(View root){
      categoryImage = (NetworkImageView)root.findViewById(R.id.categories_thumb);
      categoryImageOpaque = (ImageView)root.findViewById(R.id.categories_thumb_opaque);
      title = (TextView)root.findViewById(R.id.category_title);
    }
  }
}
