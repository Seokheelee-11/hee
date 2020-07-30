package com.shinhancard.chatbot.controller.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class EventHistoryResponse {

	//결과메세지
	private String resultCode;
	private String responseMessage;
	
	//request 기본 입력값
	private String eventId;
	private String clnn;
	private List<String> param;
	
	private LocalDateTime date;
	
	//limit 신청시 신청 결과
	private String rewardName;
	private Integer order;
	
	private Integer eventAppliedOrder;
	private Integer dDateCount;
	
	private Map<String,String> resultInfo = new HashMap<String,String>();
	
	public EventHistoryResponse() {
		param = new ArrayList<String>();
	}
	
}