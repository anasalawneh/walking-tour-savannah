package edu.armstrong.manager;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.armstrong.util.HistoricSite;

/**
 * 
 * @author Dakota Brown, Sean Clapp
 * @since 01/14/13
 *
 * Manager that holds a static instance of all the dig sites.
 * Design based on Singleton pattern.
 */
public class HistoricSiteManager {
	public static HistoricSiteManager hsm;
	private LinkedHashMap<String, HistoricSite> mapOfSites = new LinkedHashMap<String, HistoricSite>();

	/**
	 * Constructor: default
	 */
	public HistoricSiteManager() {
		hsm = this;
	}
	
	/**
	 * Constructor: Uses populated list of dig sites
	 * 
	 * @param listOfSites - all the dig sites put into a hashmap
	 */
	public HistoricSiteManager(LinkedHashMap<String, HistoricSite> listOfSites){
		hsm = this;
		hsm.setListOfSites(listOfSites);
	}

	/**
	 * 
	 * @return
	 */
	public static HistoricSiteManager getInstanceOf() {
		return hsm;
	}

	public LinkedHashMap<String, HistoricSite> getMapOfSites() {
		return hsm.mapOfSites;
	}

	public void setListOfSites(LinkedHashMap<String, HistoricSite> listOfSites) {
		hsm.mapOfSites = listOfSites;
	}
}
