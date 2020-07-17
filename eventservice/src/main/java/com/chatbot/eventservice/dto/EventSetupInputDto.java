package com.chatbot.eventservice.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;

import lombok.Data;


@Data
public class EventSetupInputDto {
	private String eventId;
	private String startDate;
	private String endDate;
	private DateType dateType; //이벤트 명 중복 date 관리(ALL - 중복 조건 없음, MIN - 분단위, HOURS - 시간 단위, DAY - 일 단위 ~~ 중복 허용)
	private RewardType rewardType;
	private int howManyLots; // 추첨 신청 가능 건수 
	private int howManyFcfs; // 선착순 신청 가능 건수
	
	private LinkedHashMap<String, Integer> rewardInfo = new LinkedHashMap<>();
	private HashMap<String,HashMap<String,Double>> resultInfo = new HashMap<>();

	private String[] targetClnn;
	private String[] nonTargetClnn;
	
	public EventSetupInputDto() {
		this.dateType = DateType.NONE;
		this.rewardType = RewardType.DEFAULT;
	}
	
	public enum DateType{
		NONE,
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


