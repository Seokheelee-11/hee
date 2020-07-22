package com.chatbot.eventservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class EventOutputDto {

	private String resultStatus;
	private String responseMessage;
	private String eventId;
	private String clnn;
	private List<String> param;
	
	private LocalDateTime date;
	private String rewardName;
	private int orderCount;
	
	private int eventOrderCount;
	private int dDateCount;
	
	private Map<String,String> resultInfo = new HashMap<String,String>();
	
	public EventOutputDto() {
		param = new ArrayList<String>();
	}
	
}