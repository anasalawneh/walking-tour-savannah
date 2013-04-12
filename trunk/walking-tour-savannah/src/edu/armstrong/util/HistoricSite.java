package edu.armstrong.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @author Sean Clapp, Dakota Brown
 * @since 01/16/13
 * 
 *        Holds the name and Lat Lng of a dig site
 * 
 */
public class HistoricSite{
	private String name;
	private LatLng ll;
	private String img;
	private String desc;
	private String longDesc;
	private List<String> evImgsStr;
	private List<String> evDesc;
	private Boolean isVisited;
	
	public HistoricSite(String name, LatLng ll, String mainImg,
			String desc, String longDesc, List<String> evImgsStr, List<String> evDesc) {
		this.name = name;
		this.ll = ll;
		this.img = mainImg;
		this.desc = desc;
		this.longDesc = longDesc;
		this.evImgsStr = evImgsStr;
		this.evDesc = evDesc;
		this.isVisited = false;
	}

	public Boolean getIsVisited() {
		return isVisited;
	}

	public void setIsVisited(Boolean isVisited) {
		this.isVisited = isVisited;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getLl() {
		return ll;
	}

	public void setLl(LatLng ll) {
		this.ll = ll;
	}

	public Bitmap getImg(Context c) {
		
		Resources res = c.getResources();
		int resID;
		resID = res.getIdentifier(img, "drawable",c.getPackageName());
		return decodeBitmapFromResource(res, resID, 300, 300);
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Bitmap> getEvImgs(Context c) {
		List<Bitmap> evImgs = new ArrayList<Bitmap>();
		for (int i = 0; i < getEvImgsStr().size(); i++) {
			Resources res = c.getResources();
			int resID;
			resID = res.getIdentifier(getEvImgsStr().get(i), "drawable",
					c.getPackageName());
			Bitmap b = decodeBitmapFromResource(res, resID, 300, 300);
			evImgs.add(b);
		}
		return evImgs;
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


	public List<String> getEvDesc() {
		return evDesc;
	}

	public List<String> getEvImgsStr() {
		return evImgsStr;
	}

	public void setEvImgsStr(List<String> evImgsStr) {
		this.evImgsStr = evImgsStr;
	}

	public void setEvDesc(List<String> evDesc) {
		this.evDesc = evDesc;
	}
}
