package edu.armstrong.util;

import java.util.LinkedList;

public class Tour {
	private LinkedList<HistoricSite> tourRoute;
	private String tourName;
	private String tourDesc;
	
	public Tour(String tourName, String tourDesc, LinkedList<HistoricSite> tourRoute){
		this.tourName = tourName;
		this.tourDesc = tourDesc;
		this.tourRoute = tourRoute;
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
}
