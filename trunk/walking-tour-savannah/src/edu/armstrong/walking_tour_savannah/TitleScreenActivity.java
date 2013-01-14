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

public class TitleScreenActivity extends Activity {
	HistoricSiteManager hsm;
	Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_screen);
		
		//TODO: Create list of sites
		populateSites();
		
		bt = (Button)findViewById(R.id.buttonTours);
		bt.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY); //change hex values to create new colors
	
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent HubActivityIntent = new Intent(TitleScreenActivity.this, HubActivity.class);
				startActivity(HubActivityIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_title_screen, menu);
		return true;
	}
	
	private void populateSites(){
		HashMap<String, HistoricSite> listOfSites = new HashMap<String, HistoricSite>();
		XMLParser parser = new XMLParser();
		InputStream is = this.getResources().openRawResource(R.xml.sites);
		String xml = parser.getXmlFromFile(is); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element
		
		NodeList nl = doc.getElementsByTagName("site");
	    
		// looping through all sites
	    for (int i = 0; i < nl.getLength(); i++) {
	    	Element e = (Element) nl.item(i);
	    	
	        String name = parser.getValue(e, "name");
	        double lat = Double.parseDouble(parser.getValue(e, "lat"));
	        double lon = Double.parseDouble(parser.getValue(e, "lon"));
	            
	        Log.d("Added site", name);
	        listOfSites.put(name, new HistoricSite(name, new GeoPoint((int)(lat * 1E6) , (int)(lon * 1E6))));
	    }
		
		new HistoricSiteManager(listOfSites);
	}
}
