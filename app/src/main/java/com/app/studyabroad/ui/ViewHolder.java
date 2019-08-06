package com.app.studyabroad.ui;

import android.widget.ImageView;
import android.widget.TextView;

public final class ViewHolder {
	  public TextView itemTextView;
      public ImageView itemImageView;
	public TextView getItemTextView() {
		return itemTextView;
	}
	public void setItemTextView(TextView itemTextView) {
		this.itemTextView = itemTextView;
	}
	public ImageView getItemImageView() {
		return itemImageView;
	}
	public void setItemImageView(ImageView itemImageView) {
		this.itemImageView = itemImageView;
	} 
}
