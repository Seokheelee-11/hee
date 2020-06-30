package com.chatbot.eventservice.dao;

public class EventSetupResult {
	private String apply; // 이벤트 등록 가능 여부
	private String result; // date Type이 없음 or rank 수가 없음 등
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
	
	public EventSetupResult() {
		this.apply="N";
		this.result="";
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
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOverLap() {
		return overLap;
	}
	public void setOverLap(String overLap) {
		this.overLap = overLap;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public int getHowManyPeople() {
		return howManyPeople;
	}
	public void setHowManyPeople(int howManyPeople) {
		this.howManyPeople = howManyPeople;
	}
	public int getRank1() {
		return rank1;
	}
	public void setRank1(int rank1) {
		this.rank1 = rank1;
	}
	public int getRank2() {
		return rank2;
	}
	public void setRank2(int rank2) {
		this.rank2 = rank2;
	}
	public int getRank3() {
		return rank3;
	}
	public void setRank3(int rank3) {
		this.rank3 = rank3;
	}
	public int getRank4() {
		return rank4;
	}
	public void setRank4(int rank4) {
		this.rank4 = rank4;
	}
	public int getRank5() {
		return rank5;
	}
	public void setRank5(int rank5) {
		this.rank5 = rank5;
	}
	
	
}
