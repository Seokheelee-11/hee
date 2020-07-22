package com.chatbot.eventservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.chatbot.eventservice.domain.EventSetup.DateType;
import com.chatbot.eventservice.domain.EventSetup.RewardType;

import lombok.Data;


@Data
public class EventSetupInputDto {
	private String eventId;
	private String startDate;
	private String endDate;
	private DateType dateType; //이벤트 명 중복 date 관리(ALL - 중복 조건 없음, MIN - 분단위, HOURS - 시간 단위, DAY - 일 단위 ~~ 중복 허용)
	private RewardType rewardType;
	
	private LinkedHashMap<String, Integer> rewardInfo = new LinkedHashMap<>();
	private HashMap<String,HashMap<String,String>> resultInfo = new HashMap<>();

	private List<String> targetClnn;
	private List<String> nonTargetClnn;
	
	public LocalDateTime parsedStartDate() {
//		LocalDateTime zdt = LocalDateTime.of(LocalDateTime.parse(startDate), ZoneId.of("Asia/Seoul"));
//		System.out.println(zdt);
//		return zdt;
		return LocalDateTime.parse(startDate);
	}
	
	public LocalDateTime parsedEndDate() {
//		LocalDateTime zdt = LocalDateTime.of(LocalDateTime.parse(endDate), ZoneId.of("Asia/Seoul"));
//		System.out.println(zdt);
//		return zdt;
		return LocalDateTime.parse(endDate);
	}
	
	public EventSetupInputDto() {
		this.dateType = DateType.DEFAULT;
		this.rewardType = RewardType.DEFAULT;
		targetClnn = new ArrayList<String>();
		nonTargetClnn = new ArrayList<String>();
	}
	

}


