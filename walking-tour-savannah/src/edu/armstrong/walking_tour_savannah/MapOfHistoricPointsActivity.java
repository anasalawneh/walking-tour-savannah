package edu.armstrong.walking_tour_savannah;

import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.r;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;

import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

/**
 * This activity will hold the map of downtown Savannah with all the points of
 * interest on it.
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapOfHistoricPointsActivity extends MapActivity {

	GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Requires API 11- NEED TO DECIDE!!!
		setUpMapIfNeeded();

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (map == null) {
			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				// The Map is verified. It is now safe to manipulate the map.
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				HashMap<String, HistoricSite> listOfSites = new HashMap<String, HistoricSite>();
				listOfSites = HistoricSiteManager.getInstanceOf()
						.getListOfSites();

				for (HistoricSite hs : listOfSites.values()) {
					map.addMarker(new MarkerOptions().position(hs.getGp())
							.title(hs.getName()));
				}

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
								.getListOfSites().get(title);
						
						Resources res = getResources();
						String imgName = hs.getImg();
						int resID = res.getIdentifier(imgName, "drawable", getPackageName());
						Drawable drawable = res.getDrawable(resID);
						
						ImageView iv = ((ImageView) markerInfo.findViewById(R.id.ivInfoWindowMain));
					    iv.setImageDrawable(drawable);
								
						TextView txtTitle = ((TextView) markerInfo
								.findViewById(R.id.txtInfoWindowTitle));

						if (title != null) {
							SpannableString titleText = new SpannableString(title);
							titleText.setSpan(
									new ForegroundColorSpan(Color.RED), 0,
									titleText.length(), 0);

							txtTitle.setText(titleText);

						} else {
							txtTitle.setText("");
						}

						TextView txtType = ((TextView) markerInfo.findViewById(R.id.txtInfoWindowEventType));
						txtType.setText("We'll put more details laterrr!");

						return markerInfo;
					}
				});
			}
		}
	}
}