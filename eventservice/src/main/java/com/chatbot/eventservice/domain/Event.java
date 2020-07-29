package com.chatbot.eventservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//@Document("EVENT")
@Data
public class Event {
	@Id
	public String id;

	private String eventId;
	private String clnn;
	
	private int orderCount; // 인벤트명 중복 허용시 한 고객이 신청한 횟수
	private LocalDateTime lastModDt;
	
	private List<History> logs;

	public Event() {
		this.orderCount = 0;
		this.logs = new ArrayList<History>();
		
	}
	
	public void historyLogAdd(History inputHistory) {
		this.logs.add(inputHistory);
	}

	public int historyLogLength() {
		System.out.println("size 들어옴");
		return this.logs.size();
	}
	
	public int getHistoryLogOrderCount() {
			return this.logs.get(logs.size()-1).getOrderCount();
	}
	public List<String> getHistoryLogParam() {
		return this.logs.get(logs.size()-1).getParam();
	}
	
	public History getHistoryEnd() {
		return this.logs.get(logs.size()-1);
	}
	
	public List<String> getHistoryLogRewards(){
		List<String> returnString = new ArrayList<String>();
		
		for(int i = 0; i< logs.size();i++) {
			returnString.add(this.logs.get(i).rewardName);
		}
		return returnString;
	}
	
	@Data	
	public static class History{
		private int orderNumber;
		private List<String> param;
		private LocalDateTime regDate;
		private String rewardName;
		
		public History() {
			param = new ArrayList<String>();
		}

	}	
}
