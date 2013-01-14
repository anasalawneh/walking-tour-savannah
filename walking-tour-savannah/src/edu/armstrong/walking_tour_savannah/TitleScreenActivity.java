package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TitleScreenActivity extends Activity {
	Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_screen);
		bt = (Button)findViewById(R.id.buttonTours);
		bt.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY); //change hex values to create new colors
	
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent HubActivityIntent = new Intent(TitleScreenActivity.this, HubActivity.class);
				startActivity(HubActivityIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_title_screen, menu);
		return true;
	}
}
