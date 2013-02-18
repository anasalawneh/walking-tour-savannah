package edu.armstrong.walking_tour_savannah;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.manager.TourManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.util.Tour;
import edu.armstrong.util.XMLParser;

/**
 * 
 * @author Dakota Brown, Sean Clapp
 * @since 01/14/13
 * 
 *        Title screen activity that transitions to the different sections of
 *        the app. It is also responsible for parsing the .xml file containing
 *        archaeological locations
 */
public class TitleScreenActivity extends Activity {

	Button btnToursList, btnSitesList, btnMap;
	private Dialog mSplashDialog;
	boolean showSplash = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		if(HistoricSiteManager.getInstanceOf() == null){
			showSplash = true;
			showSplashScreen();
			new PopulateSites().execute();
		}

		
		setContentView(R.layout.activity_title_screen);
		// Button definitions
		btnToursList = (Button) findViewById(R.id.buttonTours);
		btnSitesList = (Button) findViewById(R.id.buttonSites);
		btnMap = (Button) findViewById(R.id.buttonMap);

		btnToursList.getBackground().setColorFilter(
				Color.parseColor("#d76969"), PorterDuff.Mode.MULTIPLY);
		btnSitesList.getBackground().setColorFilter(
				Color.parseColor("#4ea956"), PorterDuff.Mode.MULTIPLY);
		btnMap.getBackground().setColorFilter(Color.parseColor("#4887ab"),
				PorterDuff.Mode.MULTIPLY);

		// On click open the ToursListActivity, a list of all the available
		// tours
		btnToursList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toursActivityIntent = new Intent(
						TitleScreenActivity.this, ToursListActivity.class);
				startActivity(toursActivityIntent);
			}
		});

		// on click open the SiteListActivity, a list of all the sites
		btnSitesList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent siteListActivityIntent = new Intent(
						TitleScreenActivity.this, SiteListActivity.class);
				startActivity(siteListActivityIntent);
			}
		});

		// on click open the MapActivity, a map of savannah with points of
		// interest.
		btnMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mapActivityIntent = new Intent(TitleScreenActivity.this,
						MapOfHistoricPointsActivity.class);
				startActivity(mapActivityIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_title_screen, menu);
		return true;
	}

	/**
	 * Parses .xml file containing dig sites and adds them to a hash map
	 */
	private void populateSites() {
		LinkedHashMap<String, HistoricSite> listOfSites = new LinkedHashMap<String, HistoricSite>();

		// reads the .xml file into a string
		XMLParser parser = new XMLParser();
		InputStream is = this.getResources().openRawResource(R.raw.sites);
		String xml = parser.getXmlFromFile(is); // getting XML

		// sets xml up to be read by tags
		Document doc = parser.getDomElement(xml); // getting DOM element

		// get all tags named "site"
		NodeList nl = doc.getElementsByTagName("site");

		// looping through all sites
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);

			Resources res = getResources();
			int resID;

			// children tag values
			String name = parser.getValue(e, "name");
			double lat = Double.parseDouble(parser.getValue(e, "lat"));
			double lon = Double.parseDouble(parser.getValue(e, "lon"));

			String mainImgName = parser.getValue(e, "img");
			resID = res
					.getIdentifier(mainImgName, "drawable", getPackageName());
			Drawable mainImg = new BitmapDrawable(getResources(),
					decodeBitmapFromResource(res, resID, 200, 200));

			String desc = parser.getValue(e, "desc");
			String longDesc = parser.getValue(e, "longDesc");

			// evidence images
			NodeList ei = e.getElementsByTagName("evImg");
			List<Drawable> evImgs = new ArrayList<Drawable>();
			for (int j = 0; j < ei.getLength(); j++) {
				String imgName = parser.getElementValue(ei.item(j));
				resID = res
						.getIdentifier(imgName, "drawable", getPackageName());
				Drawable drawable = new BitmapDrawable(getResources(),
						decodeBitmapFromResource(res, resID, 200, 200));
				evImgs.add(drawable);
			}

			// evidence descriptions
			NodeList ed = e.getElementsByTagName("evDesc");
			List<String> evDesc = new ArrayList<String>();
			for (int j = 0; j < ed.getLength(); j++) {
				String d = parser.getElementValue(ed.item(j));
				evDesc.add(d);
			}

			Log.d("Added site", name);
			listOfSites.put(name, new HistoricSite(name, new LatLng(lat, lon),
					mainImg, desc, longDesc, evImgs, evDesc));
		}

		// populate for use throughout the app
		new HistoricSiteManager(listOfSites);
	}

	private void populateTours() {
		// reads the .xml file into a string
		LinkedHashMap<String, HistoricSite> mapOfSites = HistoricSiteManager
				.getInstanceOf().getMapOfSites();
		LinkedHashMap<String, Tour> mapOfTours = new LinkedHashMap<String, Tour>();

		Resources res = this.getResources();

		XMLParser parser = new XMLParser();
		InputStream is = res.openRawResource(R.raw.tours);
		String xml = parser.getXmlFromFile(is); // getting XML

		// sets xml up to be read by tags
		Document doc = parser.getDomElement(xml); // getting DOM element

		// get all tags named "tour"
		NodeList nl = doc.getElementsByTagName("tour");

		// looping through all sites
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);

			String tourName = parser.getValue(e, "name");
			String tourDesc = parser.getValue(e, "desc");
			String tourImg = parser.getValue(e, "img");
			int resID = res
					.getIdentifier(tourImg, "drawable", getPackageName());
			Drawable drawable = new BitmapDrawable(getResources(),
					decodeBitmapFromResource(res, resID, 200, 200));

			NodeList sites = e.getElementsByTagName("site");
			LinkedList<HistoricSite> tourRoute = new LinkedList<HistoricSite>();
			for (int j = 0; j < sites.getLength(); j++) {
				if (sites.item(j) != null)
					tourRoute.push(mapOfSites.get(parser.getElementValue(sites
							.item(j))));
			}
			mapOfTours.put(tourName, new Tour(tourName, tourDesc, tourRoute, drawable));
		}
		new TourManager(mapOfTours);
	}

	public static Bitmap decodeBitmapFromResource(Resources res, int resId,
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

	public static int calculateInSampleSize(BitmapFactory.Options options,
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
	
	protected void showSplashScreen() {
	    mSplashDialog = new Dialog(this, R.style.SplashScreen);
	    mSplashDialog.setContentView(R.layout.splash);
	    mSplashDialog.setCancelable(false);
	    mSplashDialog.show();
	     
	    // Set Runnable to remove splash screen just in case
	    final Handler handler = new Handler();
	   
	    handler.postDelayed(new Runnable() {
	      @Override
	      public void run() {
	    	if(showSplash){
	    		handler.postDelayed(this, 1000);
	    	}else{
	    		removeSplashScreen();
	    	}
	      }
	    }, 1000);
	}
	
	protected void removeSplashScreen() {
	    if (mSplashDialog != null) {
	        mSplashDialog.dismiss();
	        mSplashDialog = null;
	    }
	}
	
    private class PopulateSites extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			populateSites();
			populateTours();
			
			TitleScreenActivity.this.showSplash = false;
			return null;
		}
  }  
	 
}
