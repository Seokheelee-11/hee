package com.chatbot.eventservice.dto;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("eventsetup")
@Data
public class EventSetup {
	private String eventId;
	private String startDate;
	private String endDate;
	private String date; // 신청시간
	private String overLap; // 이벤트 명 중복 허용 여부(매일 출석 등)
	private String dateType; // "overLap" 필드가 "Y"인 경우 이벤트 명 중복 date 관리("s" : 매초,"d" : 매일, "m" : 매월, "y" : 매년)
	private String limit; // 이벤트 신청 가능 명수 제한 여부(선착순 등)
	private int howManyPeople; // "limit" 필드가 "Y"인 경우 이벤트 신청 가능 명수
	private int rank1;
	private int rank2;
	private int rank3;
	private int rank4;
	private int rank5;
	
	public EventSetup() {
		this.dateType="s";
		this.overLap="N";
		this.limit="N";
		this.howManyPeople=0;
		this.rank1=0;
		this.rank2=0;
		this.rank3=0;
		this.rank4=0;
		this.rank5=0;
	}
	
	

}
