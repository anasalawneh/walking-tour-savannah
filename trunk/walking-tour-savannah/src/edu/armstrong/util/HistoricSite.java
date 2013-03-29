package edu.armstrong.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @author Sean Clapp, Dakota Brown
 * @since 01/16/13
 * 
 *        Holds the name and Lat Lng of a dig site
 * 
 */
public class HistoricSite{
	private String name;
	private LatLng ll;
	private Bitmap img;
	private String desc;
	private String longDesc;
	private List<Bitmap> evImgs;
	private List<String> evImgsStr;
	private List<String> evDesc;
	private Boolean isVisited;

//	public HistoricSite(String name, LatLng ll, Bitmap mainImg,
//			String desc, String longDesc, List<Bitmap> evImgs, List<String> evDesc) {
//		this.name = name;
//		this.ll = ll;
//		this.img = mainImg;
//		this.desc = desc;
//		this.longDesc = longDesc;
//		this.evImgs = evImgs;
//		this.evDesc = evDesc;
//		this.isVisited = false;
//	}
	
	public HistoricSite(String name, LatLng ll, Bitmap mainImg,
			String desc, String longDesc, List<String> evImgsStr, List<String> evDesc) {
		this.name = name;
		this.ll = ll;
		this.img = mainImg;
		this.desc = desc;
		this.longDesc = longDesc;
		this.evImgsStr = evImgsStr;
		this.evDesc = evDesc;
		this.isVisited = false;
		this.evImgs = new ArrayList<Bitmap>();
	}

	public Boolean getIsVisited() {
		return isVisited;
	}

	public void setIsVisited(Boolean isVisited) {
		this.isVisited = isVisited;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
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

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Bitmap> getEvImgs() {
		return evImgs;
	}

	public void setEvImgs(List<Bitmap> evImgs) {
		this.evImgs = evImgs;
	}

	public List<String> getEvDesc() {
		return evDesc;
	}

	public List<String> getEvImgsStr() {
		return evImgsStr;
	}

	public void setEvImgsStr(List<String> evImgsStr) {
		this.evImgsStr = evImgsStr;
	}

	public void setEvDesc(List<String> evDesc) {
		this.evDesc = evDesc;
	}
}
