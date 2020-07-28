package com.shinhancard.chatbot.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.shinhancard.chatbot.domain.EventInfo.DateType;
import com.shinhancard.chatbot.domain.EventInfo.EventInfoResultCode;
import com.shinhancard.chatbot.domain.EventInfo.RewardType;

import lombok.Data;

@Data
public class EventInfoOutput {
	
	private String resultCode;
	private String resultMessage;
	
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
	
	public void setResult(EventInfoResultCode result) {
		this.resultCode = result.getResultCode();
		this.resultMessage = result.getResultMessage();
	}


	
	//TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것 
	

	

	
}

