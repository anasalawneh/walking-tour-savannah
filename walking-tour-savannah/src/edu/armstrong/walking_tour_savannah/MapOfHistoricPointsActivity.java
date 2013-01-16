package edu.armstrong.walking_tour_savannah;

import java.util.Collection;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.maps.MapActivity;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.overlay.SiteOverlay;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.view.SiteView;

/**
 * This activity will hold the map of downtown Savannah with all the points of
 * interest on it.
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class MapOfHistoricPointsActivity extends MapActivity {

	SiteView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		
		
		 Collection<HistoricSite> listOfSites = HistoricSiteManager
		 .getInstanceOf().getListOfSites().values();
		
		 sv = (SiteView) findViewById(R.id.siteView1);
		 sv.setBuiltInZoomControls(true);
		
		 List<Overlay> mapOverlays = sv.getOverlays();
		 Drawable drawable = this.getResources().getDrawable(
		 R.drawable.ic_launcher);
		
		 SiteOverlay itemizedOverlay = new SiteOverlay(drawable, this);
		
		 for (HistoricSite hs : listOfSites) {
		 itemizedOverlay.addOverlay(new OverlayItem(hs.getGp(), "Test",
		 "Testing"));
		 }
		
		 mapOverlays.add(itemizedOverlay);
	}

	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_map, menu);
	// return true;
	// }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
