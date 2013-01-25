//package edu.armstrong.walking_tour_savannah;
//
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.GestureDetector.OnGestureListener;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.animation.AnimationUtils;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Gallery;
//import android.widget.Gallery.LayoutParams;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.ViewSwitcher;
//import edu.armstrong.manager.HistoricSiteManager;
//import edu.armstrong.util.HistoricSite;
//
//public class ImageSwitcher extends Activity implements
//		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, OnGestureListener {
//	private List<Drawable> mThumbIds;
//	private List<Drawable> mImageIds;
//	private List<String> mDescs;
//
//	private TextView tv;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		String siteName = getIntent().getStringExtra("site");
//		HistoricSite hs = HistoricSiteManager.getInstanceOf().getListOfSites()
//				.get(siteName);
//		mImageIds = hs.getEvImgs();
//		mImageIds.add(0, hs.getImg());
//		mThumbIds = hs.getEvImgs();
//		mThumbIds.add(0, hs.getImg());
//		mDescs = hs.getEvDesc();
//		mDescs.add(0, hs.getDesc());
//
//		setContentView(R.layout.activity_image_switcher_test);
//
//		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
//		
//		tv = (TextView) findViewById(R.id.imageSwitcherTextView);
//
//		mSwitcher.setFactory(this);
//		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
//				android.R.anim.fade_in));
//		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
//				android.R.anim.fade_out));
//
//		Gallery g = (Gallery) findViewById(R.id.gallery);
//		g.setAdapter(new ImageAdapter(this));
//		g.setOnItemSelectedListener(this);
//	}
//
//	public void onItemSelected(AdapterView<?> parent, View v, int position,
//			long id) {
//		mSwitcher.setImageDrawable(mImageIds.get(position));
//		tv.setText(mDescs.get(position));
//	}
//
//	public void onNothingSelected(AdapterView<?> parent) {
//	}
//
//	public View makeView() {
//		ImageView i = new ImageView(this);
//		i.setBackgroundColor(0xFF000000);
//		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
//		i.setLayoutParams(new ImageSwitcher.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		return i;
//	}
//
//	private ImageSwitcher mSwitcher;
//
//	public class ImageAdapter extends BaseAdapter {
//		public ImageAdapter(Context c) {
//			mContext = c;
//		}
//
//		public int getCount() {
//			return mThumbIds.size();
//		}
//
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ImageView i = new ImageView(mContext);
//
//			i.setImageDrawable(mThumbIds.get(position));
//			i.setAdjustViewBounds(true);
//			i.setLayoutParams(new Gallery.LayoutParams(
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			i.setBackgroundResource(R.drawable.picture_frame);
//			return i;
//		}
//
//		private Context mContext;
//
//	}
//
//	@Override
//	public boolean onDown(MotionEvent e) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//			float velocityY) {
//		
//		return true;
//	}
//
//	@Override
//	public void onLongPress(MotionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onShowPress(MotionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean onSingleTapUp(MotionEvent e) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}
