package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class TitleScreenActivity extends Activity {
	Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_screen);
		bt = (Button)findViewById(R.id.buttonTours);
		bt.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY); //change hex values to create new colors
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_title_screen, menu);
		return true;
	}
}
