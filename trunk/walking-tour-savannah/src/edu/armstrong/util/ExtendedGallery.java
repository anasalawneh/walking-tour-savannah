package edu.armstrong.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class ExtendedGallery extends Gallery {
	public ExtendedGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ExtendedGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExtendedGallery(Context context) {
		super(context);
	}

	@SuppressWarnings("deprecation")
	@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {

    	  int kEvent;
    	  if(isScrollingLeft(e1, e2)){ //Check if scrolling left
    	    kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
    	  }
    	  else{ //Otherwise scrolling right
    	    kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
    	  }
    	  onKeyDown(kEvent, null);
    	  return true;  
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
    	  return e2.getX() > e1.getX();
    	}
}