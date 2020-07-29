package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HistoryLog {

	private int orderNumber;
	private List<String> param;
	private LocalDateTime regDate;
	private String rewardName;
	
	public HistoryLog() {
		param = new ArrayList<String>();
	}
}
