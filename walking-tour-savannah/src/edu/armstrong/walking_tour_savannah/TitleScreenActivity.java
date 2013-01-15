package edu.armstrong.walking_tour_savannah;

import java.io.InputStream;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
 * Title screen activity that transitions to the different sections of the app.
 * It is also responsible for parsing the .xml file containing archaeological
 * locations
 */
public class TitleScreenActivity extends Activity {

	Button btnTours;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_screen);

		populateSites();

		//"Tours" Button definition
		btnTours = (Button) findViewById(R.id.buttonTours);
		btnTours.getBackground().setColorFilter(0xFF00FF00,
				PorterDuff.Mode.MULTIPLY);
		// On click open the HubActivity
		btnTours.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent hubActivityIntent = new Intent(TitleScreenActivity.this,
						HubActivity.class);
				startActivity(hubActivityIntent);
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
		
		//reads the .xml file into a string
		XMLParser parser = new XMLParser();
		InputStream is = this.getResources().openRawResource(R.raw.sites);
		String xml = parser.getXmlFromFile(is); // getting XML
		
		//sets xml up to be read by tags
		Document doc = parser.getDomElement(xml); // getting DOM element

		//get all tags named "site"
		NodeList nl = doc.getElementsByTagName("site");

		// looping through all sites
		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);

			//children tag values
			String name = parser.getValue(e, "name");
			double lat = Double.parseDouble(parser.getValue(e, "lat"));
			double lon = Double.parseDouble(parser.getValue(e, "lon"));

			Log.d("Added site", name);
			listOfSites.put(name, new HistoricSite(name, 
					new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6))));
		}
		
		//populate for use throughout the app
		new HistoricSiteManager(listOfSites);
	}
}
