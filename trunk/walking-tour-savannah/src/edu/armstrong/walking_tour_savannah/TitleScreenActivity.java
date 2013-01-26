package edu.armstrong.walking_tour_savannah;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;

import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.util.XMLParser;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_screen);

		populateSites();

		// Button definitions
		btnToursList = (Button) findViewById(R.id.buttonTours);
		btnSitesList = (Button) findViewById(R.id.buttonSites);
		btnMap = (Button) findViewById(R.id.buttonMap);

		btnToursList.getBackground().setColorFilter(Color.parseColor("#d76969"),
				PorterDuff.Mode.MULTIPLY);
		btnSitesList.getBackground().setColorFilter(Color.parseColor("#4ea956"),
				PorterDuff.Mode.MULTIPLY);
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
		HashMap<String, HistoricSite> listOfSites = new HashMap<String, HistoricSite>();

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
		    resID = res.getIdentifier(mainImgName, "drawable", getPackageName());
			Drawable mainImg = res.getDrawable(resID);
			
			String desc = parser.getValue(e, "desc");
			
			//evidence images
			NodeList ei = e.getElementsByTagName("evImg");
			List<Drawable> evImgs = new ArrayList<Drawable>();
			for(int j = 0; j < ei.getLength(); j++){
				String imgName = parser.getElementValue(ei.item(j));
				resID = res.getIdentifier(imgName, "drawable", getPackageName());
				Drawable drawable = new BitmapDrawable(getResources(), decodeBitmapFromResource(res, resID, 200, 200));
				evImgs.add(drawable);
			}
			
			//evidence descriptions
			NodeList ed = e.getElementsByTagName("evDesc");
			List<String> evDesc = new ArrayList<String>();
			for(int j = 0; j < ed.getLength(); j++){
				String d = parser.getElementValue(ed.item(j));
				evDesc.add(d);
			}
			
			Log.d("Added site", name);
			listOfSites.put(name, new HistoricSite(name, new LatLng(lat, lon), mainImg, desc, evImgs, evDesc));
		}

		// populate for use throughout the app
		new HistoricSiteManager(listOfSites);
	}
	
	public static Bitmap decodeBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}
}