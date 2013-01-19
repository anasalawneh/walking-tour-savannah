package edu.armstrong.walking_tour_savannah;

import java.io.InputStream;
import java.util.HashMap;

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
import android.graphics.PorterDuff;
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

		btnToursList.getBackground().setColorFilter(0xFF00FF00,
				PorterDuff.Mode.MULTIPLY);
		btnSitesList.getBackground().setColorFilter(0xFF00FF00,
				PorterDuff.Mode.MULTIPLY);
		btnMap.getBackground().setColorFilter(0xFF00FF00,
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

			// children tag values
			String name = parser.getValue(e, "name");
			double lat = Double.parseDouble(parser.getValue(e, "lat"));
			double lon = Double.parseDouble(parser.getValue(e, "lon"));
			String img = parser.getValue(e, "img");
			String desc = parser.getValue(e, "desc");
			
			Log.d("Added site", name);
			listOfSites.put(name, new HistoricSite(name, new LatLng(lat, lon), img, desc));
		}

		// populate for use throughout the app
		new HistoricSiteManager(listOfSites);
	}
}
