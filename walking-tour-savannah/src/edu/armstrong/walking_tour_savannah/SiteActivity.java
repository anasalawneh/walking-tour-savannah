package edu.armstrong.walking_tour_savannah;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.overlay.SiteOverlay;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.view.SiteView;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class SiteActivity extends MapActivity {
	SiteView sv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.site_layout);
		
		
		Collection<HistoricSite> listOfSites = HistoricSiteManager.getInstanceOf().getListOfSites().values();
		
		sv = (SiteView) findViewById(R.id.siteView1);	
		sv.setBuiltInZoomControls(true);
		
		List<Overlay> mapOverlays = sv.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
		
		SiteOverlay itemizedOverlay = new SiteOverlay(drawable, this);
		
		for(HistoricSite hs : listOfSites){
			itemizedOverlay.addOverlay(new OverlayItem(hs.getGp(),"Test", "Testing"));
		}
		
		mapOverlays.add(itemizedOverlay);		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
