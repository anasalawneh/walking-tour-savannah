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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import edu.armstrong.manager.FontManager;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.manager.TourManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.util.Tour;
import edu.armstrong.util.XMLParser;

/**
 * 
 * @author Dakota Brown, Sean Clapp
 * @since 04/18/13
 * 
 *        Title screen activity that transitions to the different sections of
 *        the app. It is also responsible for parsing the .xml file containing
 *        archaeological locations and tours
 */
public class TitleScreenActivity extends Activity {

	//navigation buttons
	private ImageButton btnToursList, btnSitesList, btnMap, btnMoreInfo;
	//splash screen
	private Dialog mSplashDialog;
	private  Toast tourToast;
	private  Toast siteToast;

	boolean showSplash = true;
	
	private Handler h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//show splash screen if the historic site manager hasn't been populated
		if (HistoricSiteManager.getInstanceOf() == null) {
			showSplash = true;
			showSplashScreen();
			//Async task to populate the historic site and tour managers
			new PopulateSites().execute();
		}

		setContentView(R.layout.activity_title_screen);

		h = new Handler();
		
		tourToast = Toast.makeText(getApplicationContext(), "List of Tours Loading...",Toast.LENGTH_SHORT);
		siteToast = Toast.makeText(getApplicationContext(), "List of Site Loading...",Toast.LENGTH_SHORT);
		
		TextView tv = (TextView) findViewById(R.id.textViewDigSavTitleText);
		tv.setTypeface(FontManager.Trashed(TitleScreenActivity.this));

		// Button definitions
		btnToursList = (ImageButton) findViewById(R.id.buttonTours);
		btnSitesList = (ImageButton) findViewById(R.id.buttonSites);
		btnMap = (ImageButton) findViewById(R.id.buttonMap);
		btnMoreInfo = (ImageButton) findViewById(R.id.buttonMoreInfo);

		//show list of all available tours
		btnToursList.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				siteToast.show();
				
				Runnable r = new Runnable(){
				
					public void run(){
						tourToast.show();
						Intent toursActivityIntent = new Intent(
								TitleScreenActivity.this, ToursListActivity.class);
						startActivity(toursActivityIntent);
					}
				};
				
				h.post(r);
				
			}
		});

		//show list of all available sites
		btnSitesList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				siteToast.show();
				
				Runnable r = new Runnable(){
				
					public void run(){
						Intent siteListActivityIntent = new Intent(
						TitleScreenActivity.this, SiteListActivity.class);
						startActivity(siteListActivityIntent);
					}
				};
				
				h.post(r);
				
			}
		});

		//show map of all sites
		btnMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mapActivityIntent = new Intent(TitleScreenActivity.this,
						MapOfHistoricPointsActivity.class);
				startActivity(mapActivityIntent);
			}
		});

		//show more info page
		btnMoreInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent moreInfoActivityIntent = new Intent(
						TitleScreenActivity.this, MoreInfoActivity.class);
				startActivity(moreInfoActivityIntent);
			}
		});
	}

	//method to display the splash screen
	protected void showSplashScreen() {
		//set the layout of the dialog
		mSplashDialog = new Dialog(this, R.style.SplashScreen);
		mSplashDialog.setContentView(R.layout.splash);

		// changed font
		TextView splashTitle = (TextView) mSplashDialog.findViewById(R.id.splashTitle);
		splashTitle.setTypeface(FontManager.Trashed(TitleScreenActivity.this));

		mSplashDialog.setCancelable(false);
		mSplashDialog.show();

		// check if the splash screen is still needed
		final Handler handler = new Handler();

 		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (showSplash) {
					handler.postDelayed(this, 1000);
				} else {
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

	//populate the HistoricSiteManager and ToursManager
	private class PopulateSites extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			populateSites();
			populateTours();

			//set the splash screen to false after finished
			TitleScreenActivity.this.showSplash = false;
			return null;
		}

		/**
		 * Parses .xml file containing dig sites and adds them to a hash map
		 */
		private void populateSites() {
			LinkedHashMap<String, HistoricSite> listOfSites = new LinkedHashMap<String, HistoricSite>();

			// reads the .xml file into a string
			XMLParser parser = new XMLParser();
			InputStream is = TitleScreenActivity.this.getResources()
					.openRawResource(R.raw.sites);
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

				String mainImg = parser.getValue(e, "img");
				String desc = parser.getValue(e, "desc");
				String longDesc = parser.getValue(e, "longDesc");

				// evidence images
				NodeList ei = e.getElementsByTagName("evImg");
				// img string names
				List<String> evImgStr = new ArrayList<String>();
				for (int j = 0; j < ei.getLength(); j++) {
					String imgName = parser.getElementValue(ei.item(j));
					evImgStr.add(imgName);
				}

				// evidence descriptions
				NodeList ed = e.getElementsByTagName("evDesc");
				List<String> evDesc = new ArrayList<String>();
				for (int j = 0; j < ed.getLength(); j++) {
					String d = parser.getElementValue(ed.item(j));
					evDesc.add(d);
				}

				Log.d("Added site", name);

				listOfSites.put(name, new HistoricSite(name, new LatLng(lat,
						lon), mainImg, desc, longDesc, evImgStr, evDesc));
			}

			// populate for use throughout the app
			new HistoricSiteManager(listOfSites);
		}

		private void populateTours() {
			// reads the .xml file into a string
			LinkedHashMap<String, HistoricSite> mapOfSites = HistoricSiteManager
					.getInstanceOf().getMapOfSites();
			LinkedHashMap<String, Tour> mapOfTours = new LinkedHashMap<String, Tour>();

			Resources res = getResources();

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

				NodeList sites = e.getElementsByTagName("site");
				LinkedList<HistoricSite> tourRoute = new LinkedList<HistoricSite>();
				for (int j = 0; j < sites.getLength(); j++) {
					if (sites.item(j) != null)
						tourRoute.add(mapOfSites.get(parser
								.getElementValue(sites.item(j))));
				}

				mapOfTours.put(tourName, new Tour(tourName, tourDesc,
						tourRoute, tourImg));
			}
			new TourManager(mapOfTours);
		}
	}

}
