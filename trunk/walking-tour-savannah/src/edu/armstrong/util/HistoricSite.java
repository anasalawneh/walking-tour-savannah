package edu.armstrong.util;

import com.google.android.maps.GeoPoint;

public class HistoricSite {
	private String name;
	private GeoPoint gp;

	public HistoricSite(String name, GeoPoint gp){
		this.name = name;
		this.gp = gp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeoPoint getGp() {
		return gp;
	}

	public void setGp(GeoPoint gp) {
		this.gp = gp;
	}
}
