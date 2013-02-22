package edu.armstrong.util;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Collection of sites in order of their appearance in the tour.
 * 
 * @author Sean Clapp, Dakota Brown
 * 
 */
public class Tour {
	private LinkedList<HistoricSite> tourRoute;
	private String tourName;
	private String tourDesc;
	private Bitmap img;

	public Tour(String tourName, String tourDesc,
			LinkedList<HistoricSite> tourRoute, Bitmap img) {
		this.tourName = tourName;
		this.tourDesc = tourDesc;
		this.tourRoute = tourRoute;
		this.img = img;
	}

	public LinkedList<HistoricSite> getTourRoute() {
		return tourRoute;
	}

	public void setTourRoute(LinkedList<HistoricSite> tourRoute) {
		this.tourRoute = tourRoute;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public String getTourDesc() {
		return tourDesc;
	}

	public void setTourDesc(String tourDesc) {
		this.tourDesc = tourDesc;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

}
