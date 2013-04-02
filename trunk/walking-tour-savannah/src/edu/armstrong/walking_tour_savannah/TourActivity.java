package edu.armstrong.walking_tour_savannah;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.armstrong.manager.TourManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.walking_tour_savannah.R.color;

/**
 * This activity will hold the routes for the tour. This list is different from
 * the SiteLists because the tour will only display the sites associated with
 * that tour. Also each site will have an additional option to select if the
 * site has been visited yet or not.
 * 
 * contentView: activity_tour
 * 
 * Uses - TableLayout: TableLayoutTourSites TableRow: table_row_tour_site
 * 
 * @since 02/06/2013
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class TourActivity extends Activity {

	TableLayout tableLayoutTourSiteList;
	private LinkedList<HistoricSite> tourRoute;
	CheckBox cb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour);

		String tourName = getIntent().getStringExtra("tour");
		tableLayoutTourSiteList = (TableLayout) findViewById(R.id.TableLayoutTourSites);

		TourManager tmgr = TourManager.getInstanceOf();
		tourRoute = tmgr.getMapOfTours().get(tourName).getTourRoute();

		for (final HistoricSite hs : tourRoute) {
			final TableRow tourListItem = (TableRow) getLayoutInflater().inflate(
					R.layout.table_row_tour_site, null);

			tourListItem.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));

			cb = (CheckBox) tourListItem.findViewById(R.id.checkBox1);
			
			cb.setChecked(hs.getIsVisited());

			cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (!hs.getIsVisited())
						hs.setIsVisited(true);
					else
						hs.setIsVisited(false);
				}
			});

			TextView tvName = ((TextView) tourListItem
					.findViewById(R.id.textViewTourSiteName));
			TextView tvDesc = ((TextView) tourListItem
					.findViewById(R.id.textViewTourSiteDesc));
			tvName.setText(hs.getName());
			tvDesc.setText(hs.getDesc());

			Bitmap img = hs.getImg();
			ImageView ivSite = (ImageView) tourListItem
					.findViewById(R.id.imageViewTourSite);
			ivSite.setImageBitmap(img);

			// set onclick listener for directions here
			tourListItem.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					cb = (CheckBox) tourListItem.findViewById(R.id.checkBox1);
					hs.setIsVisited(true);
					cb.setChecked(hs.getIsVisited());
					
					Intent toursActivityIntent = new Intent(TourActivity.this,
							SiteDescriptionActivity.class);
					toursActivityIntent.putExtra("site", hs.getName());
					startActivity(toursActivityIntent);
				}
			});

			tableLayoutTourSiteList.addView(tourListItem);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tour, menu);
		return true;
	}
}
