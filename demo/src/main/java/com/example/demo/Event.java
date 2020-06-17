package com.example.demo;

public class Event {
	private String id;
	private String clnn;
	private String eventId;
	private String date;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClnn() {
		return clnn;
	}

	public void setClnn(String clnn) {
		this.clnn = clnn;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	 @Override
	  public String toString() {
	    return String.format(
		        "Event[clnn=%s, Event='%s']",
	        clnn, eventId);
	  }

	
}