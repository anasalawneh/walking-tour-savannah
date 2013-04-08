package edu.armstrong.walking_tour_savannah;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
 * contentview: activity_site_desription
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
@SuppressWarnings("deprecation")
public class SiteDescriptionActivity extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

	/**
	 * Lists to hold the images and descriptions
	 */
	private List<Bitmap> myImageIds = null;
	private List<String> mDescs = null;
	private HistoricSite hs = null;
	private TextView tvSiteDesc = null;
	private TextView tvImageNumber = null;
	private Button mapButton = null;
	private Button dirButton = null;
	Gallery g = null;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myImageIds = new ArrayList<Bitmap>();
		String siteName = getIntent().getStringExtra("site");
		hs = HistoricSiteManager.getInstanceOf().getMapOfSites().get(siteName);
		populateLists();

		setTitle("" + siteName);
		setContentView(R.layout.activity_site_description);

		tvSiteDesc = (TextView) findViewById(R.id.imageSwitcherTextView);
		tvImageNumber = (TextView) findViewById(R.id.textViewImageNumber);
		tvSiteDesc.setTypeface(FontManager
				.DroidSans(SiteDescriptionActivity.this));
		tvSiteDesc.setMovementMethod(LinkMovementMethod.getInstance());

		mapButton = (Button) findViewById(R.id.mapButton);
		dirButton = (Button) findViewById(R.id.directionsButton);
		mapButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mapActivityIntent = new Intent(
						SiteDescriptionActivity.this,
						MapOfHistoricPointsActivity.class);
				mapActivityIntent.putExtra("siteName", hs.getName());
				startActivity(mapActivityIntent);
			}
		});

		dirButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(Intent.ACTION_VIEW,
				/** Using the web based turn by turn directions url. */
				Uri.parse("http://maps.google.com/maps?" + "saddr=" + "&daddr="
						+ hs.getLl().latitude + "," + hs.getLl().longitude));
				intent.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				startActivity(intent);
			}
		});

		g = (Gallery) findViewById(R.id.gallerySiteDesc);
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_site_description, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View v, int position,
			long id) {
		try {
			tvSiteDesc.setText(Html.fromHtml(mDescs.get(position).toString()));
			tvImageNumber.setText("Image " + (position + 1) + " of "
					+ myImageIds.size() + " (Swipe for more!)");
		} catch (IndexOutOfBoundsException e) {

		}
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
			return i;
		}

	}// ImageAdapter

	/**
	 * populate the lists with info from the historic site manager
	 */
	private void populateLists() {
		myImageIds = new ArrayList<Bitmap>();
		if (!myImageIds.contains(hs.getImg(SiteDescriptionActivity.this))) {
			myImageIds.add(hs.getImg(SiteDescriptionActivity.this));
		}
		for (Bitmap b : hs.getEvImgs(SiteDescriptionActivity.this)) {
			if (!myImageIds.contains(b))
				myImageIds.add(b);
		}
		mDescs = new ArrayList<String>();
		mDescs.add(hs.getLongDesc());
		for (String s : hs.getEvDesc()) {
			mDescs.add(s);
		}
	}
}
