package com.chatbot.eventservice.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("EVENT")
@Data
public class EventInputDto {
	@Id
	public String id;

	/*DB에 저장할 field*/
	private int applyCount; // 인벤트명 중복 허용시 한 고객이 신청한 횟수
	private int dateCount; // 이벤트 시작일로부터 dateCount(applyCount와 동일하면 매일, 매월 출석중인 상태)
	private int limitCount; // limit == "Y"인 경우에만 return. 이벤트 신청 순서임(key : eventId)
	private int rank;	// limit인 경우 rank 순위 return
	private String eventId;
	private String clnn;
	private LocalDateTime date;
	/*
	 * private String param1; private String param2; private String param3;
	 */
	private String[] param;
	
	/*DB에 저장안할 field*/
	private String apply; // 이벤트 신청 여부 "Y"인 경우 정상적으로 이벤트 신청, "N"이면 이벤트 신청에 문제가 있음
	private String result; // 이벤트 신청 결과 이미 신청하여 이용 불가함 or 선착순 끝남 등
	
	
	public EventInputDto() {
		this.apply="N";
		this.result="";
		this.applyCount = 0;
		this.dateCount = 0;
		this.limitCount = 0;
		this.rank = 0;
		/*
		 * this.param1=""; this.param2=""; this.param3="";
		 */
	}

//	public int compareTo(Event event) {
//		System.out.println(this.getDate());
//		System.out.println(event.getDate());
//		if(this.date < event.getDate())	{
//			return -1;
//		} else if(Integer.parseInt(this.date) == Integer.parseInt(event.getDate())) {
//			return 0;
//		} else {
//			return 1;
//		}
//	}
	
}