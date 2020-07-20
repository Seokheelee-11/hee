package com.chatbot.eventservice.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("EVENTMANAGE")
@Data
public class EventSetup {
	@Id
	public String id;
	
	private String eventId;
	private String startDate;
	private String endDate;
	private LocalDateTime date;
	private DateType dateType; //이벤트 명 중복 date 관리(ALL - 중복 조건 없음, MIN - 분단위, HOURS - 시간 단위, DAY - 일 단위 ~~ 중복 허용)
	private RewardType rewardType;
	
	private LinkedHashMap<String, Integer> rewardInfo = new LinkedHashMap<>();
	private HashMap<String,HashMap<String,String>> resultInfo = new HashMap<>();

	private String[] targetClnn;
	private String[] nonTargetClnn;
	
	private int totalCount; // 추첨, 선착순 신청 가능 건수 
	private String closingStatus;
	
	public EventSetup() {
		this.closingStatus = "N";
	}
	
	public enum DateType{
		DEFAULT,
		ALL,
		MIN,
		HOUR,
		DAY,
		MONTH,
		YEAR;
		
	}
	public enum RewardType{
		DEFAULT,
		FCFS,
		RANDOM,
		RANDOMPROB, // 랜덤 확률
		QUIZ // QUIZ는 여기 있으면 안됨 나중에 수정할 수 있도록
	}
}


