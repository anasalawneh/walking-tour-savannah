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

//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//			float velocityY) {
//		return false;
//	}
	
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {

        //limit the max speed in either direction
        if (velocityX > 1200.0f)
        {
            velocityX = 1200.0f;
        }
        else if(velocityX < 1200.0f)
        {
            velocityX = -1200.0f;
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }

}