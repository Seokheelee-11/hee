package com.chatbot.eventservice.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.chatbot.eventservice.domain.EventSetup.DateType;
import com.chatbot.eventservice.domain.EventSetup.RewardType;

import lombok.Data;


@Data
public class EventSetupOutputDto {
	private String resultStatus;
	private String responseMessage;
	private String eventId;
	private String startDate;
	private String endDate;
	private DateType dateType; //이벤트 명 중복 date 관리(ALL - 중복 조건 없음, MIN - 분단위, HOURS - 시간 단위, DAY - 일 단위 ~~ 중복 허용)
	private RewardType rewardType;

	private LinkedHashMap<String, Integer> rewardInfo = new LinkedHashMap<>();
	private HashMap<String,HashMap<String,String>> resultInfo = new HashMap<>();

	private String[] targetClnn;
	private String[] nonTargetClnn;
	
	private int totalCount; // 추첨, 선착순 신청 가능 건수 
	
	public EventSetupOutputDto() {
		this.resultStatus = "N";
	}
}
