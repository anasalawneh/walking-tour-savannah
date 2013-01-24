package edu.armstrong.walking_tour_savannah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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
import android.widget.ViewSwitcher;

public class ImageSwitcherTest extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {
	private List<Drawable> mThumbIds; 
	private List<Drawable> mImageIds;
	private List<String> mDescs;
	
	private TextView tv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		String siteName = getIntent().getStringExtra("site");
		HistoricSite hs = HistoricSiteManager.getInstanceOf().getListOfSites().get(siteName);
		mImageIds = new ArrayList<Drawable>();
		mImageIds.add(hs.getImg());
		mImageIds.addAll(hs.getEvImgs());
		
		mThumbIds = new ArrayList<Drawable>();
		mThumbIds.add(hs.getImg());
		mThumbIds.addAll(hs.getEvImgs());
		
		mDescs = new ArrayList<String>();
		mDescs.add(hs.getDesc());
		mDescs.addAll(hs.getEvDesc());
		
		
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

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		mSwitcher.setImageDrawable(mImageIds.get(position));
		tv.setText(mDescs.get(position));
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
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


	// private Integer[] mThumbIds = {
	// R.drawable.sample_thumb_0, R.drawable.sample_thumb_1,
	// R.drawable.sample_thumb_2, R.drawable.sample_thumb_3,
	// R.drawable.sample_thumb_4, R.drawable.sample_thumb_5,
	// R.drawable.sample_thumb_6, R.drawable.sample_thumb_7};

	// private Integer[] mImageIds = {
	// R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,
	// R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
	// R.drawable.sample_6, R.drawable.sample_7};

}
