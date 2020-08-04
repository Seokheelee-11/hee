package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EventHistoryLog {

	private Integer order;
	private List<String> param;
	private LocalDateTime regDate;
	private String rewardName;
	
	public EventHistoryLog() {
		order = 1;
		param = new ArrayList<String>();
		rewardName = "default";
		regDate = LocalDateTime.now();
	}
	


	
	
	
}
