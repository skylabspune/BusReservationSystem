package com.busreservationsystem.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TicketDto {
	private int ticketId;
	private String source;
	private String destination;
	private int routeId;
	private int noOfTickets;
	private int totalCost;
	private String username;
	
	public int getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public int getRouteId() {
		return routeId;
	}
	
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	
	public int getNoOfTickets() {
		return noOfTickets;
	}
	
	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}
	
	public int getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}