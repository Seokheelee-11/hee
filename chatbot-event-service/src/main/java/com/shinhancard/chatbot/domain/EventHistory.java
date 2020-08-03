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
	private String displayName;
	
	private Integer lastOrder;
	private List<EventHistoryLog> logs; 
	//TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것 
	
	private LocalDateTime lastModDt;
	
	public void addLog(EventHistoryLog log) {
		this.logs.add(log);
	}
	
	public void update(EventHistory newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();

	}
	public void updateLastOrder(int count) {
		this.lastOrder = count;
		this.lastModDt = LocalDateTime.now();
	}
	


	public Boolean getValidationEventIdInput() {
		if(this.eventId.isEmpty()) {
			return false;
		}
		return true;
	}
	public Boolean getValidationClnnInput() {
		if(this.clnn.isEmpty()) {
			return false;
		}
		return true;
	}
	
	

}
