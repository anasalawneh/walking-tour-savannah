package edu.armstrong.util;

import java.util.LinkedList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

/**
 * Collection of sites in order of their appearance in the tour.
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class Tour {
	private LinkedList<HistoricSite> tourRoute;
	private String tourName;
	private String tourDesc;
	private String img;

	public Tour(String tourName, String tourDesc,
			LinkedList<HistoricSite> tourRoute,String img) {
		this.tourName = tourName;
		this.tourDesc = tourDesc;
		this.tourRoute = tourRoute;
		this.img = img;
	}

	public LinkedList<HistoricSite> getTourRoute() {
		return tourRoute;
	}

	public void setTourRoute(LinkedList<HistoricSite> tourRoute) {
		this.tourRoute = tourRoute;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public String getTourDesc() {
		return tourDesc;
	}

	public void setTourDesc(String tourDesc) {
		this.tourDesc = tourDesc;
	}

	public Bitmap getImg(Context c) {
		Resources res = c.getResources();
		int resID = res.getIdentifier(img, "drawable", c.getPackageName());
		Bitmap b = decodeBitmapFromResource(res, resID, 300, 300);
		return b;
	}
	
	public Bitmap decodeBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}


	public void setImg(String img) {
		this.img = img;
	}

}
