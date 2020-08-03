package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class EventInfo {

	@Id
	private String id;

	private String eventId;
	private String displayName;

	private LocalDateTime startDt;
	private LocalDateTime endDt;

	// 중복 신청 관련 field
	private Boolean overLapTF;
	private OverLapType overLapDateType;
	private Integer overLapDateCount;
	private Boolean includeDateTF;

	// limit 관련 field
	private Boolean rewardTF;
	private RewardType rewardType;
	private LinkedHashMap<String, Double> rewardInfo = new LinkedHashMap<>();

	// quiz 신청 관련 field
	private Boolean quizTF;
	private List<String> quizAnswer;

	// 결과 field
	private HashMap<String, HashMap<String, String>> resultInfo = new HashMap<>();

	// 대상자 선정
	private List<String> targetClnn;
	private List<String> nonTargetClnn;

	public void update(EventInfo newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();

	}

	public EventInfo() {
		targetClnn = new ArrayList<String>();
		nonTargetClnn = new ArrayList<String>();
		quizAnswer = new ArrayList<String>();
	}

	// TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것

	public boolean isNotValid() {

		// TODO :: 신청가능 기간 및 참여 가능한 상태인지 체크하는 로직
		// this.startDt,
		/*
		 * if () { return true; }
		 */
		return false;
	}

	public Boolean needOverLapLogics() {

		if (!this.overLapDateType.equals(OverLapType.ALLTIME)) {
			return true;
		}
		return false;

	}

	public Boolean getValidationRewardDefaultInput() {
		if (this.rewardType == null || CollectionUtils.isEmpty(this.rewardInfo)) {
			return false;
		}
		return true;
	}

	public Boolean getValidationRewardRandomProbInput() {

		// randomprob의 값이 1을 넘었는지?
		if (this.getTotalProb() > 1) {
			return false;
		}
		return true;
	}

	public Boolean getValidationOverLapInput() {
		if (this.overLapTF) {
			if (this.overLapDateType == null || this.overLapDateCount == null || this.includeDateTF == null) {
				return false;
			}
		}
		return true;
	}

	public Boolean getValidationQuizAnswerInput() {
		if (CollectionUtils.isEmpty(this.quizAnswer)) {
			return false;
		}
		return true;
	}

	public Boolean getValidationEventId() {
		if (StringUtils.isEmpty(this.eventId)) {
			return false;
		}
		return true;
	}

	public Boolean getValidationDateInput() {
		if (this.getStartDt() == null || this.getEndDt() == null) {
			return false;
		}
		return true;
	}

	public Boolean getValidationDateOrder() {
		if (this.getStartDt().isAfter(this.getEndDt())) {
			return false;
		}
		return true;
	}

	public Double getTotalProb() {
		Double totalProb = 0.0;
		Set<String> keys = this.getRewardInfo().keySet();

		for (String key : keys) {
			// System.out.println(key);
			totalProb += this.getRewardInfo().get(key);
		}
		return totalProb;
	}
	
	public Integer getTotalCount() {
		int result = 0;
		Set<String> keys = this.rewardInfo.keySet();

		for (String key : keys) {
			result += rewardInfo.get(key);
		}
		return result;
	}

	public Boolean getEventDateValidate(LocalDateTime date) {
		if (this.startDt.isBefore(date) || this.endDt.isAfter(date)) {
			return false;
		}
		return true;
	}

	public boolean isRewardRandom() {
		if (this.rewardType == RewardType.RANDOM) {
			return true;
		}

		return false;
	}

	public boolean isRewardFCFS() {
		if (this.rewardType == RewardType.FCFS) {
			return true;
		}

		return false;
	}

	public boolean isRewardRandomProb() {
		if (this.rewardType == RewardType.RANDOMPROB) {
			return true;
		}

		return false;
	}

	public enum RewardType {
		FCFS, RANDOM, RANDOMPROB; // 랜덤 확률
		
		// QUIZ // QUIZ는 여기 있으면 안됨 나중에 수정할 수 있도록
	}

	public enum OverLapType {
		ALLTIME, MINUTE, HOUR, DAY, MONTH, YEAR;
		
		public Boolean isAllTime() {
			if( this.equals(ALLTIME)) {
				return true;
			}
			return false;
		}
		public Boolean isMinute() {
			if( this.equals(MINUTE)) {
				return true;
			}
			return false;
		}
		public Boolean isHour() {
			if( this.equals(HOUR)) {
				return true;
			}
			return false;
		}
		public Boolean isDay() {
			if( this.equals(DAY)) {
				return true;
			}
			return false;
		}
		public Boolean isMonth() {
			if( this.equals(MONTH)) {
				return true;
			}
			return false;
		}
		public Boolean isYear() {
			if( this.equals(YEAR)) {
				return true;
			}
			return false;
		}
	}

}
