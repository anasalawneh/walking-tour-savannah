package edu.armstrong.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @author Sean Clapp, Dakota Brown
 * @since 01/16/13
 * 
 *        Holds the name and LatLng of a dig site
 * 
 */
public class HistoricSite {
	private String name;
	private LatLng ll;
	private String img;
	private String desc;



	public HistoricSite(String name, LatLng ll, String img, String desc) {
		this.name = name;
		this.ll = ll;
		this.img = img;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
