package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class EventHistory {
	private String id;
	private String eventId;
	private String clnn;
	private int orderCount;
	private List<HistoryLog> logs; 
	//TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것 
	
	private LocalDateTime lastModDt;
	
	public void addLog(HistoryLog log) {
		this.logs.add(log);
	}
	
	public void updateOrderCount(int count) {
		this.orderCount = count;
		this.lastModDt = LocalDateTime.now();
	}
}
