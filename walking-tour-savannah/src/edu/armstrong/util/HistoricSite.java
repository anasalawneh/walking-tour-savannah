package edu.armstrong.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @author Sean Clapp, Dakota Brown
 * @since 01/16/13
 * 
 * Holds the name and GeoPoint of a dig site
 * 
 */
public class HistoricSite {
	private String name;
	private LatLng ll;

	public HistoricSite(String name, LatLng ll){
		this.name = name;
		this.ll = ll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getGp() {
		return ll;
	}

	public void setGp(LatLng ll) {
		this.ll = ll;
	}
}
