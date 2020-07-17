package com.chatbot.eventservice.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.chatbot.eventservice.dto.EventSetupInputDto.DateType;
import com.chatbot.eventservice.dto.EventSetupInputDto.RewardType;

import lombok.Data;

@Document("EVENTMANAGE")
@Data
public class EventSetup {
	@Id
	public String id;
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
	
	private int totalCount;
	private String closingStatus;
	
	public EventSetup() {
		this.closingStatus = "N";
	}
}


