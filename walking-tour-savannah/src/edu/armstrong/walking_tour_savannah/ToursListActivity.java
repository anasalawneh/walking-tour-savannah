package edu.armstrong.walking_tour_savannah;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.armstrong.manager.TourManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.util.Tour;

/**
 * This activity will hold the list of avilable tours, when a tour is clicked,
 * the activity TourActivity will launch and show the sites on the route.
 * 
 * contentView: activity_tours_list
 * uses -
 * TableLayout: TableLayoutTourList
 * TableRow: table_row_tour
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class ToursListActivity extends Activity {

	TableLayout tableLayoutTourList;
	LinkedHashMap<String, Tour> tours;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tours_list);

		tableLayoutTourList = (TableLayout) findViewById(R.id.TableLayoutTourList);
		tours = TourManager.getInstanceOf().getMapOfTours();
		
		/**
		 * this adds the tablerows to the tablelayout dynamically, depending on
		 * the list of sites.
		 */

		for (final Tour tour : tours.values()) {

			TableRow tourListItem = (TableRow) getLayoutInflater().inflate(
					R.layout.table_row_tour, null);
			tourListItem.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));

			TextView tvName = ((TextView) tourListItem
					.findViewById(R.id.textViewTourName));
			TextView tvDesc = ((TextView) tourListItem
					.findViewById(R.id.textViewTourDesc));

			/**
			 * when we have desc we can just call hs.getDesc() to populate the
			 * info
			 */
			tvName.setText(tour.getTourName());
			tvDesc.setText(tour.getTourDesc());

			/**
			 * Update the image of the tablerow to the image of the historic
			 * site
			 */

			Drawable drawable = tour.getImg();
			ImageView ivTour = (ImageView) tourListItem
					.findViewById(R.id.imageViewTourListImg1);
			ivTour.setImageDrawable(drawable);

			/**
			 * set the onclick listener to take us to the tour activity.
			 */
			tourListItem.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					LinkedList<HistoricSite> route = tour.getTourRoute();
//					final Intent intent = new Intent(Intent.ACTION_VIEW,
//					/** Using the web based turn by turn directions url. */
//					Uri.parse("http://maps.google.com/maps?" + "saddr="
//							+ route.peek().getLl().latitude + ","
//							+ route.peek().getLl().longitude + "&daddr="
//							+ route.peekLast().getLl().latitude + ","
//							+ route.peekLast().getLl().longitude));
//					intent.setClassName("com.google.android.apps.maps",
//							"com.google.android.maps.MapsActivity");
//					startActivity(intent);
					
					Intent toursActivityIntent = new Intent(
							ToursListActivity.this, TourActivity.class);
					toursActivityIntent.putExtra("tour", tour.getTourName());
					startActivity(toursActivityIntent);
				}
			});

			tableLayoutTourList.addView(tourListItem);
		}
	}

}
