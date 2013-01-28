package edu.armstrong.manager;

import java.util.LinkedHashMap;

import edu.armstrong.util.Tour;

/**
 * 
 * @author Dakota Brown, Sean Clapp
 * @since 01/14/13
 *
 * Manager that holds a static instance of all the dig sites.
 * Design based on Singleton pattern.
 */
public class TourManager {
	public static TourManager tm;
	private LinkedHashMap<String, Tour> mapOfTours = new LinkedHashMap<String, Tour>();

	/**
	 * Constructor: default
	 */
	public TourManager() {
		tm = this;
	}
	
	/**
	 * Constructor: Uses populated list of dig sites
	 * 
	 * @param listOfSites - all the dig sites put into a hashmap
	 */
	public TourManager(LinkedHashMap<String, Tour> mapOfSites){
		tm = this;
		tm.setMapOfTours(mapOfSites);
	}

	/**
	 * 
	 * @return
	 */
	public static TourManager getInstanceOf() {
		return tm;
	}

	public LinkedHashMap<String, Tour> getMapOfTours() {
		return tm.mapOfTours;
	}

	public void setMapOfTours(LinkedHashMap<String, Tour> mapOfTours) {
		tm.mapOfTours = mapOfTours;
	}
}
