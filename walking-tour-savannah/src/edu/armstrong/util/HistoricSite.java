package edu.armstrong.util;

import java.util.List;

import android.graphics.drawable.Drawable;

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
	private Drawable img;
	private String desc;
	private List<Drawable> evImgs;
	private List<String> evDesc;

	public HistoricSite(String name, LatLng ll, Drawable mainImg,
			String desc, List<Drawable> evImgs, List<String> evDesc) {
		this.name = name;
		this.ll = ll;
		this.img = mainImg;
		this.desc = desc;
		this.evImgs = evImgs;
		this.evDesc = evDesc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getLl() {
		return ll;
	}

	public void setLl(LatLng ll) {
		this.ll = ll;
	}

	public Drawable getImg() {
		return img;
	}

	public void setImg(Drawable img) {
		this.img = img;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Drawable> getEvImgs() {
		return evImgs;
	}

	public void setEvImgs(List<Drawable> evImgs) {
		this.evImgs = evImgs;
	}

	public List<String> getEvDesc() {
		return evDesc;
	}

	public void setEvDesc(List<String> evDesc) {
		this.evDesc = evDesc;
	}
}
