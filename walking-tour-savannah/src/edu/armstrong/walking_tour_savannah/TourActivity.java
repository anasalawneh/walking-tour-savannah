package edu.armstrong.walking_tour_savannah;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import edu.armstrong.util.HistoricSite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;

/**
 * This activity will hold the routes for the tour. This list is different from
 * the SiteLists because the tour will only display the sites associated with
 * that tour. Also each site will have an additional option to select if the
 * site has been visited yet or not. 
 * 
 * @since 02/06/2013
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class TourActivity extends Activity {

	TableLayout tableLayoutTourSiteList;
	private LinkedList<HistoricSite> tourRoute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tour, menu);
		return true;
	}

}
