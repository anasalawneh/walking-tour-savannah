package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HubActivity extends Activity{
	Button btnSites;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hub_layout);
		btnSites = (Button)findViewById(R.id.buttonSites);
		
		btnSites.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent SiteViewIntent = new Intent(HubActivity.this, SiteView.class);
				startActivity(SiteViewIntent);
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_title_screen, menu);
		return true;
	}
}
