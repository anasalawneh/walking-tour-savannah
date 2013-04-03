package edu.armstrong.walking_tour_savannah;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * contentview: activity_site_list uses - TableLayout: TableLayoutSiteList
 * TableRows:
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class SiteListActivity extends Activity {

	TableLayout tableLayoutSiteList;
	HistoricSiteManager hsmgr;
	private HistoricSite hs = null;

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

			TextView tvName = ((TextView) siteListItem
					.findViewById(R.id.textViewSiteTableRowName));
			tvName.setTypeface(FontManager.Trashed(SiteListActivity.this));

			TextView tvDesc = ((TextView) siteListItem
					.findViewById(R.id.textViewSiteTableRowDesc));
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
					loadImgs(hs);
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
}
