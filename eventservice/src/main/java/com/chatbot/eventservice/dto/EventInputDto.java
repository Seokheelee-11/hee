
package com.chatbot.eventservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class EventInputDto {

	private String eventId;
	private String clnn;
	private List<String> param;
	
	public EventInputDto() {
		param = new ArrayList<String>();
	}
	
}