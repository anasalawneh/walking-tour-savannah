package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class MoreInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info);
		
		String string = "Although Savannah is known for its historic preservation, the archaeology, unseen by most, has been mostly forgotten. As a result, many archaeological sites have been destroyed. Digging Savannah is a grant-funded program at Armstrong Atlantic State University that engages students in solving the problem of archaeological site loss in Savannah.\n\nThis app allows you to visit archaeology sites that have been explored, but only lists sites on property that is open to the public. This is only a small sampling of archaeological sites around Savannah, as nearly every historic building sits on an archaeological site.\n\nIt is illegal to dig on public land, and ill-advised to dig on private land. City of Savannah, state, and federal laws prohibit all digging on public property. Taking artifacts from an archaeology site is like taking words out of a Shakespeare play. The words don’t make any sense without their surrounding context of sentences and paragraphs. Artifacts don’t mean anything without their surrounding context of soil layers, artifacts, and features like foundations and hearths.\n\nLearn more about the <a href='https://diggingsavannah.wordpress.com'>Digging Savannah project</a> or find us on <a href='https://www.facebook.com/DiggingSavannah'>Facebook</a>.\n\n<a href='www.armstrong.edu'>Armstrong's website</a>";
		
		TextView tv = (TextView) findViewById(R.id.moreInfoText);
		tv.setText(Html.fromHtml(string));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_more_info, menu);
		return true;
	}

}
