package com.busreservationsystem.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MapLocationDto {
	private double latitude;
	private double longitude;
	private int cId;

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}