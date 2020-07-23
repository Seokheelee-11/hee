package com.chatbot.eventservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//@Document("EVENTMANAGE")
@Data
public class EventSetup {
	@Id
	public String id;
	
	private String eventId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime date;
	private DateType dateType; //이벤트 명 중복 date 관리(ALL - 중복 조건 없음, MIN - 분단위, HOURS - 시간 단위, DAY - 일 단위 ~~ 중복 허용)
	private RewardType rewardType;
	
	private LinkedHashMap<String, Double> rewardInfo = new LinkedHashMap<>();
	private HashMap<String,HashMap<String,String>> resultInfo = new HashMap<>();

	private List<String> targetClnn;
	private List<String> nonTargetClnn;
	
	private int totalCount; // 추첨, 선착순 신청 가능 건수 
	//enum이나 ,변하지 않는 string 형태(define)로 바뀌면 좋을 듯 
	private String closingStatus;
	

	
	
	public EventSetup() {
		this.closingStatus = "N";
		targetClnn = new ArrayList<String>();
		nonTargetClnn = new ArrayList<String>();
	}
	
	//DateType 보다는 중복주기가 어떰?
	// 1분/1시간 뿐만 아니라, 값을 하나 더 받아와서 3시간 단위 1주일 단위 등을 추가
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
		//QUIZ // QUIZ는 여기 있으면 안됨 나중에 수정할 수 있도록
	}
}


