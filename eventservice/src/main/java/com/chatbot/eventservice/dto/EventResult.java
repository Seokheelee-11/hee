package com.chatbot.eventservice.dto;

public class EventResult {
	private String apply; // 이벤트 신청 여부 "Y"인 경우 정상적으로 이벤트 신청, "N"이면 이벤트 신청에 문제가 있음
	private String result; // 이벤트 신청 결과 이미 신청하여 이용 불가함 or 선착순 끝남 등
	private String eventId;
	private String clnn;
	private String applyDate;
	private int applyCount; // 인벤트명 중복 허용시 한 고객이 신청한 횟수
	private int dateCount; // 이벤트 시작일로부터 dateCount(applyCount와 동일하면 매일, 매월 출석중인 상태)
	private int limitCount; // limit == "Y"인 경우에만 return. 이벤트 신청 순서임(key : eventId)
	private int rank;	// limit인 경우 rank 순위 return
	private String param1;
	private String param2;
	private String param3;
	
	public EventResult() {
		this.applyCount = 0;
		this.dateCount = 0;
		this.limitCount = 0;
		this.rank = 0;
		this.param1="";
		this.param2="";
		this.param3="";
	}
	
	
	public String getApply() {
		return apply;
	}
	public void setApply(String apply) {
		this.apply = apply;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getClnn() {
		return clnn;
	}
	public void setClnn(String clnn) {
		this.clnn = clnn;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public int getApplyCount() {
		return applyCount;
	}
	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}
	public int getDateCount() {
		return dateCount;
	}
	public void setDateCount(int dateCount) {
		this.dateCount = dateCount;
	}
	public int getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	
}
