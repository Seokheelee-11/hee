package com.chatbot.eventservice.dto;

public class Event {
	private String eventId;
	private String clnn;
	private String date;
	private String param1;
	private String param2;
	private String param3;
	
	public Event() {
		this.param1="";
		this.param2="";
		this.param3="";
	}
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getClnn() {
		return clnn;
	}
	public void setClnn(String clnn) {
		this.clnn = clnn;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	

	

	
}