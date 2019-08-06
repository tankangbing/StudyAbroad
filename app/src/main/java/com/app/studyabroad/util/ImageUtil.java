package com.app.studyabroad.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageUtil {

	 public static void setImageSrc(ImageView imageView,Resources res, int imagePath) {
	 	BitmapFactory.Options option = new BitmapFactory.Options();
	 	option.inSampleSize = getImageScale(res,imagePath);
	 	Bitmap bm = BitmapFactory.decodeResource(res, imagePath,option);
	 	imageView.setImageBitmap(bm);
	 }
	  
	 private static int   IMAGE_MAX_WIDTH  = 400;
	 private static int   IMAGE_MAX_HEIGHT = 400;
	  
	/**
	 * scale image to fixed height and weight
	 * 
	 * @param imagePath
	 * @return
	 */
	private static int getImageScale(Resources res, int imagePath) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		// set inJustDecodeBounds to true, allowing the caller to query the
		// bitmap info without having to allocate the
		// memory for its pixels.
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, imagePath, option);

		int scale = 1;
		while (option.outWidth / scale >= IMAGE_MAX_WIDTH
				|| option.outHeight / scale >= IMAGE_MAX_HEIGHT) {
			scale *= 2;
		}
		return scale;
	}
	
}
