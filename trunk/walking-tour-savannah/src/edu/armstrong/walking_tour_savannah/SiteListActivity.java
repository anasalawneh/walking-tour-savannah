package edu.armstrong.walking_tour_savannah;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

/**
 * This activity will hold a list of all the sites that are available to look
 * at. When a user clicks on a site, it will take them to the map activity with
 * the site highlighted. (Or another activity with more info about the site,
 * whichever works better for the app)
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class SiteListActivity extends Activity {

	TableLayout tableLayoutSiteList;

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.site_table_row_layout);

		tableLayoutSiteList = (TableLayout) findViewById(R.id.TableLayout1);
		
		/**
		 * this adds the tablerows to the tablelayout dynamically, depending on
		 * the list of sites.
		 */
		for (final HistoricSite hs : HistoricSiteManager.getInstanceOf()
				.getListOfSites().values()) {

			TableRow siteListItem = (TableRow) getLayoutInflater().inflate(
					R.layout.site_layout, null);
			siteListItem.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));

			TextView tvName = ((TextView) siteListItem.findViewById(R.id.textViewSiteName));
			TextView tvDesc = ((TextView) siteListItem.findViewById(R.id.textViewSiteDesc));

			/**
			 * when we have desc we can just call hs.getDesc() to populate the
			 * info
			 */
			tvName.setText(hs.getName());
			tvDesc.setText(hs.getDesc()); 

			/**
			 * Update the image of the tablerow to the image of the historic
			 * site
			 */
			
			Drawable drawable = hs.getImg();
			ImageView ivSite = (ImageView) siteListItem
					.findViewById(R.id.imageViewSiteListImg);
			ivSite.setImageDrawable(drawable);

			/**
			 * set the onclick listener to take us to the map position of the
			 * site (not implelements yet, thus the toast notification for
			 * testing)
			 */
			siteListItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent toursActivityIntent = new Intent(
							SiteListActivity.this, ImageSwitcherTest.class);
					toursActivityIntent.putExtra("site", hs.getName());
					startActivity(toursActivityIntent);
					
					String test = new String("you clicked " + hs.getName());
					Toast toast = Toast.makeText(
							tableLayoutSiteList.getContext(), test, 50);
					toast.show();
				}
			});

			tableLayoutSiteList.addView(siteListItem);
		}
	}
}
