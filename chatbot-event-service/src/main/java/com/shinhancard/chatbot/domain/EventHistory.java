package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class EventHistory {
	@Id
	private String id;
	
	private String eventId;
	private String clnn;
	
	private Integer lastOrder;
	private List<HistoryLog> logs; 
	//TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것 
	
	private LocalDateTime lastModDt;
	
	public void addLog(HistoryLog log) {
		this.logs.add(log);
	}
	
	public void update(EventInfo newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();

	}
	public void updateLastOrder(int count) {
		this.lastOrder = count;
		this.lastModDt = LocalDateTime.now();
	}
}
