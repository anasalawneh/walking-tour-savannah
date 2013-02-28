package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.armstrong.manager.FontManager;
import edu.armstrong.manager.HistoricSiteManager;
import edu.armstrong.util.HistoricSite;

/**
 * This activity will hold a list of all the sites that are available to look
 * at. When a user clicks on a site, it will take them to the map activity with
 * the site highlighted. (Or another activity with more info about the site,
 * whichever works better for the app)
 * 
 * contentview: activity_site_list
 * uses -
 * TableLayout: TableLayoutSiteList
 * TableRows: 
 * 
 * @author Sean Clapp, Dakota Brown 
 * 
 */
public class SiteListActivity extends Activity {

	TableLayout tableLayoutSiteList;
	HistoricSiteManager hsmgr;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site_list);

		
		tableLayoutSiteList = (TableLayout) findViewById(R.id.TableLayoutSiteList);
		hsmgr = HistoricSiteManager.getInstanceOf();
		
		/**
		 * this adds the tablerows to the tablelayout dynamically, depending on
		 * the list of sites.
		 */
		for (final HistoricSite hs : hsmgr.getMapOfSites().values()) {

			TableRow siteListItem = (TableRow) getLayoutInflater().inflate(
					R.layout.table_row_site, null);
			siteListItem.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));

			TextView tvName = ((TextView) siteListItem.findViewById(R.id.textViewSiteTableRowName));
			tvName.setTypeface(FontManager.Trashed(SiteListActivity.this));
			
			TextView tvDesc = ((TextView) siteListItem.findViewById(R.id.textViewSiteTableRowDesc));
			tvDesc.setTypeface(FontManager.DroidSans(SiteListActivity.this));

		
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
			
			Bitmap img = hs.getImg();
			ImageView ivSite = (ImageView) siteListItem
					.findViewById(R.id.imageViewSiteTableRowImg);
			ivSite.setImageBitmap(img);

			/**
			 * set the onclick listener to take us to the map position of the
			 * site (not implelements yet, thus the toast notification for
			 * testing)
			 */
			siteListItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent toursActivityIntent = new Intent(
							SiteListActivity.this, SiteDescriptionActivity.class);
					toursActivityIntent.putExtra("site", hs.getName());
					startActivity(toursActivityIntent);
				}
			});

			tableLayoutSiteList.addView(siteListItem);
		}
	}
}
