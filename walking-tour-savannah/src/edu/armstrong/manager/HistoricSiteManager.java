package edu.armstrong.manager;

import java.util.HashMap;

import edu.armstrong.util.HistoricSite;

public class HistoricSiteManager {
	public static HistoricSiteManager hsm;

	private HashMap<String, HistoricSite> listOfSites = new HashMap<String, HistoricSite>();

	public HistoricSiteManager() {
		hsm = this;
	}
	
	public HistoricSiteManager(HashMap<String, HistoricSite> listOfSites){
		hsm = this;
		hsm.setListOfSites(listOfSites);
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
