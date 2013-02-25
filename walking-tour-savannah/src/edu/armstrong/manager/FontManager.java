package edu.armstrong.manager;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager{

	public static Typeface Trashed(Context c){
		return Typeface.createFromAsset(c.getAssets(),"fonts/TRASHED.ttf");
	}
}
