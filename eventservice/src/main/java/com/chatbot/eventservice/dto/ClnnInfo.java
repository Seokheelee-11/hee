package com.chatbot.eventservice.dto;

import org.springframework.data.annotation.Id;

public class ClnnInfo {
	@Id
	private String id;
	private String clnn;
	private Event event;


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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	 @Override
	  public String toString() {
	    return String.format(
		        "Event[id=%s, clnn=%s, EventId=%s, date = %s]",
	        id, clnn, event.getEventId(), event.getDate());
	  }


}
