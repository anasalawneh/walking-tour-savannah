package edu.armstrong.walking_tour_savannah;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import edu.armstrong.manager.FontManager;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.util.XMLParser;

/**
 * This will hold the information about each site. There will be an image
 * switcher at the top and a description of the site at the bottom. These will
 * be created dynamically as each one is selected by the user to view. (from the
 * SiteListActivity).
 * 
 * contentview is activity_site_description_no_map
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
@SuppressWarnings("deprecation")
public class SiteDescriptionNoMapActivity extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

	/**
	 * Lists to hold the images and descriptions
	 */
	private List<Bitmap> myImageIds = null;
	private List<String> mDescs = null;
	private HistoricSite hs = null;
	private TextView tvSiteDesc = null;
	Gallery g = null;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String siteName = getIntent().getStringExtra("site");
		
		hs = HistoricSiteManager.getInstanceOf().getMapOfSites().get(siteName);
		populateLists();
		
		setTitle("" + siteName);
		setContentView(R.layout.activity_site_description_no_map);

		tvSiteDesc = (TextView) findViewById(R.id.imageSwitcherTextView);
		tvSiteDesc.setTypeface(FontManager.DroidSans(SiteDescriptionNoMapActivity.this));
		tvSiteDesc.setMovementMethod(LinkMovementMethod.getInstance());
		
		g = (Gallery) findViewById(R.id.gallerySiteDesc);
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_site_description, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View v, int position,
			long id) {
		tvSiteDesc.setText(Html.fromHtml(mDescs.get(position)));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public View makeView() {
		return null;
	}

	/**
	 * Image adapter for the gallery.
	 */
	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return myImageIds.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/**
		 * Create a view to hold the images in and be used for the gallery
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			i.setImageBitmap(myImageIds.get(position));
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			//i.setBackgroundColor(0xFF8C0C04);
			
			return i;
		}
	}// ImageAdapter

	
	/**
	 * poopulate the lists with info from the historic site manager
	 */
	private void populateLists() {
		myImageIds = new ArrayList<Bitmap>();
		loadImgs();
		myImageIds.add(hs.getImg());
		myImageIds.addAll(hs.getEvImgs());
		mDescs = new ArrayList<String>();
		mDescs.add(hs.getLongDesc());
		mDescs.addAll(hs.getEvDesc());
	}
	
	private void loadImgs() {

		
		// reads the .xml file into a string
		XMLParser parser = new XMLParser();
		InputStream is = SiteDescriptionNoMapActivity.this.getResources()
				.openRawResource(R.raw.sites);
		String xml = parser.getXmlFromFile(is); // getting XML

		// sets xml up to be read by tags
		Document doc = parser.getDomElement(xml); // getting DOM element

		// get all tags named "site"
		NodeList nl = doc.getElementsByTagName("site");

		for (int i = 0; i < hs.getEvImgsStr().size(); i++) {
			
			Resources res = getResources();
			int resID;

			resID = res.getIdentifier(hs.getEvImgsStr().get(i), "drawable", getPackageName());
			Bitmap mainImg = decodeBitmapFromResource(res, resID, 300, 300);
			
			Bitmap b = decodeBitmapFromResource(this.getResources(), resID,
					300, 300);
			hs.getEvImgs().add(b);
		}

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
}
