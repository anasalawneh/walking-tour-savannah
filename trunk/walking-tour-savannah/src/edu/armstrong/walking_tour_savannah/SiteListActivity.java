package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import edu.armstrong.manager.FontManager;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

/**
 * This activity will hold a list of all the sites that are available to look
 * at. When a user clicks on a site, it will take them to the map activity with
 * the site highlighted. (Or another activity with more info about the site,
 * whichever works better for the app)
 * 
 * contentview: activity_site_list uses - TableLayout: TableLayoutSiteList
 * TableRows:
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class SiteListActivity extends Activity {

	private TableLayout tableLayoutSiteList;
	Typeface trashed, droid;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site_list);

		tableLayoutSiteList = (TableLayout) findViewById(R.id.TableLayoutSiteList);
		HistoricSiteManager hsmgr = HistoricSiteManager.getInstanceOf();


		//fonts
		trashed = FontManager.Trashed(SiteListActivity.this);
		droid = FontManager.DroidSans(SiteListActivity.this);

		
		//for each site
		for (final HistoricSite hs : hsmgr.getMapOfSites().values()) {

			//create a new table row
			TableRow siteListItem = (TableRow) getLayoutInflater().inflate(R.layout.table_row_site, null);
			siteListItem.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

			//set references to text views
			TextView tvName = ((TextView) siteListItem.findViewById(R.id.textViewSiteTableRowName));
			TextView tvDesc = ((TextView) siteListItem.findViewById(R.id.textViewSiteTableRowDesc));

			tvName.setTypeface(trashed);
			tvDesc.setTypeface(droid);

			tvName.setText(hs.getName());
			tvDesc.setText(hs.getDesc());

			//set image of site
			Bitmap img = hs.getImg(SiteListActivity.this);
			ImageView ivSite = (ImageView) siteListItem.findViewById(R.id.imageViewSiteTableRowImg);
			ivSite.setImageBitmap(img);

			//go to site when site entry is clicked
			siteListItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "Site Loading...", Toast.LENGTH_SHORT).show();
					Intent toursActivityIntent = new Intent(
							SiteListActivity.this,
							SiteDescriptionActivity.class);
					toursActivityIntent.putExtra("site", hs.getName());
					startActivity(toursActivityIntent);
				}
			});

			tableLayoutSiteList.addView(siteListItem);
		}
	}
}
