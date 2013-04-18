package edu.armstrong.walking_tour_savannah;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.armstrong.manager.FontManager;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

/**
 * This activity will hold the map of downtown Savannah with all the points of
 * interest on it.
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class MapOfHistoricPointsActivity extends FragmentActivity {

	private GoogleMap map;
	private LatLngBounds.Builder bc = new LatLngBounds.Builder();
	private HashMap<String, Marker> markerMap = new HashMap<String, Marker>();
	private String goTo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		setUpMapIfNeeded();
		goTo = getIntent().getStringExtra("siteName");
	}
	
	/**
	 * populates the map and moves it to the proper position
	 */
	private void setUpMapIfNeeded() {
		// if map hasn't been created
		if (map == null) {
			//get the map from the support fragment
			SupportMapFragment s = (SupportMapFragment) this
					.getSupportFragmentManager().findFragmentById(R.id.map);
			map = s.getMap();
			
			// Check if we were successful in obtaining the map.
			if (map != null) {
				
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				//get a local copy of the site map from the site manager
				HashMap<String, HistoricSite> listOfSites = HistoricSiteManager.getInstanceOf().getMapOfSites();

				//populate the map with markers
				for (HistoricSite hs : listOfSites.values()) {
					Marker m = map.addMarker(new MarkerOptions().position(hs.getLl()).title(hs.getName()));
					markerMap.put(m.getTitle(), m);
					//add the site to the boundary list
					bc.include(hs.getLl());
				}
				
				
				//change camera listener
				map.setOnCameraChangeListener(new OnCameraChangeListener() {

				    @Override
				    public void onCameraChange(CameraPosition arg0) {
				    	// if the parent is SiteDescription, then center on that site
						if (goTo != null) {
							Marker m = markerMap.get(goTo);
							m.showInfoWindow();
							map.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(), 16));

						// else, center the map around all markers
						} else {
							map.animateCamera(CameraUpdateFactory.newLatLngBounds(bc.build(),
									30));
						}
						map.setOnCameraChangeListener(null);
				    }
				});

				//set what happens when an info window is clicked
				map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick(Marker m) {
						Intent siteDescIntent = new Intent(
								MapOfHistoricPointsActivity.this,
								SiteDescriptionNoMapActivity.class);
						siteDescIntent.putExtra("site", m.getTitle());
						startActivity(siteDescIntent);
					}
				});

				//define the info window's look and feel
				map.setInfoWindowAdapter(new InfoWindowAdapter() {
					//set layout of view
					private final View markerInfo = getLayoutInflater().inflate(R.layout.marker_info_layout, null);

					@Override
					public View getInfoWindow(Marker marker) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						String title = marker.getTitle();
						
						//grab historic site with the marker's title
						HistoricSite hs = HistoricSiteManager.getInstanceOf().getMapOfSites().get(title);

						//set the image of site
						ImageView iv = ((ImageView) markerInfo.findViewById(R.id.ivInfoWindowMain));
						iv.setImageBitmap(hs.getImg(MapOfHistoricPointsActivity.this));

						//set the site's name
						TextView txtTitle = ((TextView) markerInfo.findViewById(R.id.txtInfoWindowTitle));
						txtTitle.setTypeface(FontManager.DroidSans(MapOfHistoricPointsActivity.this));
						if (title != null) {
							SpannableString titleText = new SpannableString(title);
							txtTitle.setText(titleText);
						} else {
							txtTitle.setText("");
						}

						//set site description
						TextView txtType = ((TextView) markerInfo.findViewById(R.id.txtInfoWindowDesc));
						txtType.setTypeface(FontManager.DroidSans(MapOfHistoricPointsActivity.this));
						txtType.setText(hs.getDesc());

						return markerInfo;
					}
				});
			}
		}
	}
}
