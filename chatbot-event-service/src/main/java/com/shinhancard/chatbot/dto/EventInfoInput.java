package com.shinhancard.chatbot.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.shinhancard.chatbot.domain.EventInfo.DateType;
import com.shinhancard.chatbot.domain.EventInfo.RewardType;

import lombok.Data;

@Data
public class EventInfoInput {
	
	private String eventId;	
	private String displayName;
	
	private LocalDateTime startDt;
	private LocalDateTime endDt;
	
	private DateType overLapDateType;
	private int overLapDateCount;
	private Boolean includeDateTF;
	
	private RewardType  rewardType;
	private LinkedHashMap<String, Double> rewardInfo = new LinkedHashMap<>();
	private HashMap<String,HashMap<String,String>> resultInfo = new HashMap<>();
	
	private Boolean quizTF;
	private String quizAnswer;
	
	private List<String> targetClnn;
	private List<String> nonTargetClnn;
	

	
	public EventInfoInput() {
		this.overLapDateType=DateType.ONCE;
		this.rewardType = RewardType.DEFAULT;
		this.quizTF=false;
		targetClnn = new ArrayList<String>();
		nonTargetClnn = new ArrayList<String>();
	}

	
}

