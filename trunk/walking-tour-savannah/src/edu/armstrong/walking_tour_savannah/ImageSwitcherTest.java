package edu.armstrong.walking_tour_savannah;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

public class ImageSwitcherTest extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {
	private List<Drawable> mThumbIds;
	private List<Drawable> mImageIds;
	private List<String> mDescs;
	private HistoricSite hs;
	private TextView tv;

	private int currentPosition;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		currentPosition = 0;
		String siteName = getIntent().getStringExtra("site");
		hs = HistoricSiteManager.getInstanceOf().getListOfSites().get(siteName);
		populateLists();

		setContentView(R.layout.activity_image_switcher_test);

		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		tv = (TextView) findViewById(R.id.imageSwitcherTextView);

		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);
	}

	private void populateLists() {
		mImageIds = new ArrayList<Drawable>();
		mImageIds.add(hs.getImg());
		mImageIds.addAll(hs.getEvImgs());

		mThumbIds = new ArrayList<Drawable>();
		mThumbIds.add(hs.getImg());
		mThumbIds.addAll(hs.getEvImgs());

		mDescs = new ArrayList<String>();
		mDescs.add(hs.getDesc());
		mDescs.addAll(hs.getEvDesc());
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		currentPosition = position;
		mSwitcher.setImageDrawable(mImageIds.get(position));
		tv.setText(mDescs.get(position));
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	public View makeView() {
		@SuppressWarnings("deprecation")
		final GestureDetector gdt = new GestureDetector(new GestureListener());
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		i.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				gdt.onTouchEvent(event);
				return true;
			}
		});
		return i;
	}

	public class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				if (currentPosition < mImageIds.size()-1) {
					currentPosition++;
					mSwitcher.setImageDrawable(mImageIds.get(currentPosition));
					tv.setText(mDescs.get(currentPosition));
				}
				return true; // Right to left
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				if (currentPosition > 0) {
					currentPosition--;
					mSwitcher.setImageDrawable(mImageIds.get(currentPosition));
					tv.setText(mDescs.get(currentPosition));
				}
				return true; // Left to right
			}

			if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				return false; // Bottom to top
			} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				return false; // Top to bottom
			}
			return false;
		}
	}

	private ImageSwitcher mSwitcher;

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			i.setImageDrawable(mThumbIds.get(position));
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			i.setBackgroundResource(R.drawable.picture_frame);
			return i;
		}

		private Context mContext;

	}
}
