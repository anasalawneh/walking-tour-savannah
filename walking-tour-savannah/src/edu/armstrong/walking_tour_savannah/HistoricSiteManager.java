package edu.armstrong.walking_tour_savannah;

import java.util.HashMap;

public class HistoricSiteManager {
	public static HistoricSiteManager hsm;
	
	private HashMap<String, HistoricSite> listOfSites = new HashMap<String, HistoricSite>();

	HistoricSiteManager(){
		hsm = this;
//		listOfSites.put("someSite", new HistoricSite("String", **geocode**));
	}

	public static HistoricSiteManager getInstanceOf() {
		return hsm;
	}

	public HashMap<String, HistoricSite> getListOfSites() {
		return listOfSites;
	}

	public void setListOfSites(HashMap<String, HistoricSite> listOfSites) {
		this.listOfSites = listOfSites;
	}
}
