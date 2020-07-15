package com.chatbot.eventservice.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document("EVENTMANAGE")
@Data
public class EventSetup {
	@Id
	public String id;
	private String apply; // 이벤트 등록 가능 여부
	private String result; // date Type이 없음 or rank 수가 없음 등
	private String eventId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date; // 신청시간
//	private String overLap; // 이벤트 명 중복 허용 여부(매일 출석 등)
	private String dateType; // "overLap" 필드가 "Y"인 경우 이벤트 명 중복 date 관리("s" : 매초,"d" : 매일, "m" : 매월, "y" : 매년)
	//private String limit; // 이벤트 신청 가능 명수 제한 여부(선착순 등)
	private int howManyLots; // 추첨 신청 가능 건수 
	private int howManyFcfs; // 선착순 신청 가능 건수
	
	/*
	 * private int rank1; private int rank2; private int rank3; private int rank4;
	 * private int rank5;
	 */
	private Map<String, Integer> drawLots = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> fcfs = new LinkedHashMap<String, Integer>();
//	private int[] fcfs;

	
	public EventSetup() {
		this.apply = "N";
		this.result = "";
//		this.overLap = "N";
//		this.howManyPeople = 0;
		//this.limit = "N";
	}

}
