package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class EventInfo {

	@Id
	private String id;

	private String eventId;
	private String displayName;

	private LocalDateTime startDt;
	private LocalDateTime endDt;

	//중복 신청 관련 field
	private Boolean overLapTF;
	private OverLapType overLapDateType;
	private Integer overLapDateCount;
	private Boolean includeDateTF;

	//limit 관련 field
	private Boolean rewardTf;
	private RewardType rewardType;
	private LinkedHashMap<String, Double> rewardInfo = new LinkedHashMap<>();
	
	//quiz 신청 관련 field
	private Boolean quizTF;
	private String quizAnswer;

	//결과 field
	private HashMap<String, HashMap<String, String>> resultInfo = new HashMap<>();
	
	//대상자 선정
	private List<String> targetClnn;
	private List<String> nonTargetClnn;

	public void update(EventInfo newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();

	}

	public EventInfo() {
		targetClnn = new ArrayList<String>();
		nonTargetClnn = new ArrayList<String>();
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

	public EventResultCode.ResultCode getDefaultValidation(EventResultCode.ResultCode result){	
		// EventId가 입력되었는지?
		result = this.getValidationEventId(result);
		// Date 입력이 정상적으로 입력되었는지?
		result = this.getValidationDateInput(result);
		// OverLap 입력이 정상적으로 되었는지?
		result = this.getValidationOverLapInput(result);
		// rewardInfo 입력이 정상적으로 되었는지?
		result = this.getValidationRewardInput(result);
		// QuizAnswer 입력이 정상적으로 되었는지?
		result = this.getValidationQuizAnswerInput(result);

		return result;
	}
	
	public EventResultCode.ResultCode getValidationRewardInput(EventResultCode.ResultCode result) {
		if (this.rewardTf) {
			if (this.rewardType == null || this.rewardInfo.isEmpty()) {
				result = EventResultCode.ResultCode.FAILED_NO_LIMIT_INPUT;
			}
			// randomprob의 값이 1을 넘었는지?
			if(RewardType.RANDOMPROB.equals(this.rewardType)) {
				if (this.getTotalProb() > 1) {
					result = EventResultCode.ResultCode.FAILED_RANDOMPROB_OVER_ONE;
				}
			}
		}
		return result;
	}
	

	public EventResultCode.ResultCode getValidationOverLapInput(EventResultCode.ResultCode result) {
		if (this.overLapTF) {
			if (this.overLapDateType == null
				||this.overLapDateCount == null
				||this.includeDateTF == null) {
				result = EventResultCode.ResultCode.FAILED_NO_OVERLAP_INPUT;
			}
		}
		return result;
	}


	public EventResultCode.ResultCode getValidationQuizAnswerInput(EventResultCode.ResultCode result) {
		if (this.quizTF) {
			if (this.quizAnswer.isEmpty()) {
				result = EventResultCode.ResultCode.FAILED_NO_QUIZANSWER_INPUT;
			}
		}
		return result;
	}

	public EventResultCode.ResultCode getValidationEventId(EventResultCode.ResultCode result) {
		if(this.eventId.isEmpty()) {
			result = EventResultCode.ResultCode.FAILED_NO_EVENTID_INPUT;
		}
		return result;
	}

	public EventResultCode.ResultCode getValidationDateInput(EventResultCode.ResultCode result) {
		if (this.getStartDt() == null || this.getEndDt() == null) {
			result = EventResultCode.ResultCode.FAILED_NO_DATE_INPUT;
		}
		else if (this.getStartDt().isAfter(this.getEndDt())) {
			result = EventResultCode.ResultCode.FAILED_DATE_ORDER;
		}
		return result;
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
	public EventResultCode.ResultCode getEventDateValidate(EventResultCode.ResultCode result, LocalDateTime date) {
		if (this.startDt.isBefore(date) || this.endDt.isAfter(date)) {
			result = EventResultCode.ResultCode.FAILED_NO_APPLY_DATE;
		}
		return result;
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
	}

}
