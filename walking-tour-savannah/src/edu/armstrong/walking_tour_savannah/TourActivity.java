package edu.armstrong.walking_tour_savannah;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.armstrong.manager.FontManager;
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

			final CheckBox cb = (CheckBox) tourListItem.findViewById(R.id.checkBox1);

			tourListItem.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT));

			cb.setChecked(hs.getIsVisited());
			
			cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (!isChecked)
						hs.setIsVisited(true);
					else
						hs.setIsVisited(false);
				}
			});

			TextView tvName = ((TextView) tourListItem
					.findViewById(R.id.textViewSiteTableRowName));
			tvName.setTypeface(FontManager.Trashed(TourActivity.this));
			TextView tvDesc = ((TextView) tourListItem
					.findViewById(R.id.textViewTourSiteDesc));
			tvDesc.setTypeface(FontManager.DroidSans(TourActivity.this));
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
					
					hs.setIsVisited(true);
					cb.setChecked(hs.getIsVisited());
					loadImgs(hs);
					Intent toursActivityIntent = new Intent(TourActivity.this,
							SiteDescriptionActivity.class);
					toursActivityIntent.putExtra("site", hs.getName());
					startActivity(toursActivityIntent);
				}
			});

			tableLayoutTourSiteList.addView(tourListItem);
		}
	}
	
	private void loadImgs(HistoricSite hs) {
		//only load imgs if the hs images haven't been loaded already...
		if (hs.getEvImgs().isEmpty()) {
			for (int i = 0; i < hs.getEvImgsStr().size(); i++) {
				Resources res = getResources();
				int resID;
				resID = res.getIdentifier(hs.getEvImgsStr().get(i), "drawable",
						getPackageName());
				Bitmap b = decodeBitmapFromResource(res, resID, 300, 300);
				hs.getEvImgs().add(b);
			}
		}
	}

	public Bitmap decodeBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tour, menu);
		return true;
	}
	
	
}
