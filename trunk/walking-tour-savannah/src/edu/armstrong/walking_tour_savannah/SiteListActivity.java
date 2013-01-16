package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableRow;

/**
 * This activity will hold a list of all the sites that are available to look
 * at. When a user clicks on a site, it will take them to another activity with
 * information about the site and a link to the map activity with the site
 * highlighted.
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class SiteListActivity extends Activity {

	TableRow tableRowSiteList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.site_layout);

		// we will need to dynamically create a list of the sites and put them
		// into the tablerowsitelist here.
		tableRowSiteList = (TableRow) findViewById(R.id.tableRow1);

	}
}
