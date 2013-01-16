package edu.armstrong.walking_tour_savannah;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * This activity will hold the list of avilable tours, when a tour is clicked, a
 * tourmap activity will launch and show the route.
 * 
 * @author Sean
 * 
 */
public class ToursListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tours_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tours_list, menu);
		return true;
	}

}
