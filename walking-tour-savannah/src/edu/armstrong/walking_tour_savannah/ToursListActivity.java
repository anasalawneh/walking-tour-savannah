package edu.armstrong.walking_tour_savannah;

import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import edu.armstrong.manager.FontManager;
import edu.armstrong.manager.TourManager;
import edu.armstrong.util.HistoricSite;
import edu.armstrong.util.Tour;

/**
 * This activity will hold the list of available tours, when a tour is clicked,
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

	private TableLayout tableLayoutTourList;
	private LinkedHashMap<String, Tour> tours;
	private Typeface trashed, droid;
	
	private Handler h;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tours_list);
		
		h = new Handler();

		tableLayoutTourList = (TableLayout) findViewById(R.id.TableLayoutTourList);
		tours = TourManager.getInstanceOf().getMapOfTours();
		
		/**
		 * this adds the tablerows to the tablelayout dynamically, depending on
		 * the list of sites.
		 */
		
		trashed = FontManager.Trashed(ToursListActivity.this);
		droid = FontManager.DroidSans(ToursListActivity.this);

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
			
			tvName.setTypeface(trashed);
			tvDesc.setTypeface(droid);

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

			Bitmap img = tour.getImg(ToursListActivity.this);
			ImageView ivTour = (ImageView) tourListItem
					.findViewById(R.id.imageViewTourListImg1);
			ivTour.setImageBitmap(img);

			/**
			 * set the onclick listener to take us to the tour activity.
			 */
			tourListItem.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "Tour Loading...",
							Toast.LENGTH_SHORT).show();
					
					Runnable r = new Runnable(){
						public void run(){
							Intent toursActivityIntent = new Intent(
									ToursListActivity.this, TourActivity.class);
							toursActivityIntent.putExtra("tour", tour.getTourName());
							startActivity(toursActivityIntent);
						}
					};
					h.post(r);
				}
			});
			tableLayoutTourList.addView(tourListItem);
		}
	}
}
