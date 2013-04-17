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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
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

	GoogleMap map;
	LatLngBounds.Builder bc = new LatLngBounds.Builder();
	SupportMapFragment s;
	HashMap<String, Marker> markerMap = new HashMap<String, Marker>();
	String goTo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		if(isNetworkConnected()){
			setUpMapIfNeeded();
			goTo = getIntent().getStringExtra("siteName");
		}else{
			Toast.makeText(getApplicationContext(), "No Internet Connection Found",Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
		   return false;
		  } else
		   return true;
		 }
	
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (map == null) {
			SupportMapFragment s = (SupportMapFragment) this
					.getSupportFragmentManager().findFragmentById(R.id.map);
			map = s.getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				// The Map is verified. It is now safe to manipulate the map.
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				HashMap<String, HistoricSite> listOfSites = new HashMap<String, HistoricSite>();
				listOfSites = HistoricSiteManager.getInstanceOf()
						.getMapOfSites();

				for (HistoricSite hs : listOfSites.values()) {
					Marker m = map.addMarker(new MarkerOptions().position(
							hs.getLl()).title(hs.getName()));
					markerMap.put(m.getTitle(), m);
					bc.include(hs.getLl());
				}
				
				map.setOnCameraChangeListener(new OnCameraChangeListener() {

				    @Override
				    public void onCameraChange(CameraPosition arg0) {
				    	// if the parent is SiteDescription, then center on that site
						if (goTo != null) {
							Marker m = markerMap.get(goTo);
							m.showInfoWindow();
							map.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(), 16));

							// else, center the map
						} else {
							map.animateCamera(CameraUpdateFactory.newLatLngBounds(bc.build(),
									30));
						}
						map.setOnCameraChangeListener(null);
				    }
				});

				map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick(Marker m) {
						Intent toursActivityIntent = new Intent(
								MapOfHistoricPointsActivity.this,
								SiteDescriptionNoMapActivity.class);
						toursActivityIntent.putExtra("site", m.getTitle());
						startActivity(toursActivityIntent);
					}
				});

				map.setInfoWindowAdapter(new InfoWindowAdapter() {

					private final View markerInfo = getLayoutInflater()
							.inflate(R.layout.marker_info_layout, null);

					@Override
					public View getInfoWindow(Marker marker) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						String title = marker.getTitle();
						HistoricSite hs = HistoricSiteManager.getInstanceOf()
								.getMapOfSites().get(title);

						ImageView iv = ((ImageView) markerInfo
								.findViewById(R.id.ivInfoWindowMain));
						iv.setImageBitmap(hs.getImg(MapOfHistoricPointsActivity.this));

						TextView txtTitle = ((TextView) markerInfo
								.findViewById(R.id.txtInfoWindowTitle));
						txtTitle.setTypeface(FontManager
								.DroidSans(MapOfHistoricPointsActivity.this));
						if (title != null) {
							SpannableString titleText = new SpannableString(
									title);
							txtTitle.setText(titleText);

						} else {
							txtTitle.setText("");
						}

						TextView txtType = ((TextView) markerInfo
								.findViewById(R.id.txtInfoWindowDesc));
						txtType.setTypeface(FontManager
								.DroidSans(MapOfHistoricPointsActivity.this));
						txtType.setText(hs.getDesc());

						return markerInfo;
					}
				});
			}
		}
	}
}
