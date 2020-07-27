package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EventInfo {
	
	@Id
	private String id;
	
	private String eventId;
	
	private String displayName;
	
	private LocalDateTime startDt;
	private LocalDateTime endDt;
	
	private RewardType  rewardType;
	
	public void update(EventInfo newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();
	}
	
	//TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것 
	
	public boolean isNotValid() {
		
		//TODO :: 신청가능 기간 및 참여 가능한 상태인지 체크하는 로직 
		//this.startDt,
		/*
		if () {
			return true;
		}
		*/
		return false;
	}
	
	public boolean isRandom() {
		if (this.rewardType == RewardType.RANDOM) {
			return true;
		}
		
		return false;
	}
	
	public boolean isFCFS() {
		if (this.rewardType == RewardType.FCFS) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRandomProb() {
		if (this.rewardType == RewardType.RANDOMPROB) {
			return true;
		}
		
		return false;
	}
	
	public enum RewardType{
		DEFAULT,
		FCFS,
		RANDOM,
		RANDOMPROB; // 랜덤 확률
		//QUIZ // QUIZ는 여기 있으면 안됨 나중에 수정할 수 있도록
	}
}

