package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.shinhancard.chatbot.domain.EventInfo.EventInfoResultCode;

import lombok.Data;

@Data
public class EventInfo {

	@Id
	private String id;

	private String eventId;
	private String displayName;

	private LocalDateTime startDt;
	private LocalDateTime endDt;

	private DateType overLapDateType;
	private int overLapDateCount;
	private Boolean includeDateTF;

	private RewardType rewardType;
	private LinkedHashMap<String, Double> rewardInfo = new LinkedHashMap<>();
	private HashMap<String, HashMap<String, String>> resultInfo = new HashMap<>();

	private Boolean quizTF;
	private String quizAnswer;

	private List<String> targetClnn;
	private List<String> nonTargetClnn;

	public void update(EventInfo newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();

	}

	public EventInfo() {
		this.overLapDateType = DateType.ONCE;
		this.rewardType = RewardType.DEFAULT;
		this.quizTF = false;
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

	public EventInfoResultCode getValidationRandomProbOver(EventInfoResultCode result) {
		if (this.isRewardRandomProb()) {
			if (this.getTotalProb() > 1) {
				result = EventInfoResultCode.FAILED_RANDOMPROB_OVER_ONE;
			}
		}
		return result;
	}

	public EventInfoResultCode getValidationOverLapInput(EventInfoResultCode result) {
		if (!this.isOverLapDateOnce()) {
			if (this.overLapDateCount < 1 || this.includeDateTF == null) {
				result = EventInfoResultCode.FAILED_NO_OVERLAP_INPUT;
			}
		}
		return result;
	}

	public EventInfoResultCode getValidationRewardInfoInput(EventInfoResultCode result) {
		if (!this.isRewardDefault()) {
			if (this.rewardInfo.isEmpty()) {
				result = EventInfoResultCode.FAILED_NO_REWARDINFO_INPUT;
			}
		}
		return result;
	}

	public EventInfoResultCode getValidationQuizAnswerInput(EventInfoResultCode result) {
		if (this.quizTF) {
			if (this.quizAnswer.isEmpty()) {
				result = EventInfoResultCode.FAILED_NO_QUIZANSWER_INPUT;
			}
		}
		return result;
	}

	public EventInfoResultCode getValidationEventId(EventInfoResultCode result) {
		if (this.getEventId() == null) {
			result = EventInfoResultCode.FAILED_NO_EVENTID_INPUT;
		}
		return result;
	}

	public EventInfoResultCode getValidationDateFormat(EventInfoResultCode result) {
		if (this.getStartDt() == null || this.getEndDt() == null) {
			result = EventInfoResultCode.FAILED_NO_DATE_INPUT;
		}
		return result;
	}

	public EventInfoResultCode getValidationDate(EventInfoResultCode result) {
		if (this.getStartDt().isAfter(this.getEndDt())) {
			result = EventInfoResultCode.FAILED_DATE_ORDER;
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

	public boolean isOverLapDateOnce() {
		if (this.overLapDateType == DateType.ONCE) {
			return true;
		}
		return false;
	}

	public boolean isRewardDefault() {
		if (this.rewardType == RewardType.DEFAULT) {
			return true;
		}

		return false;
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
		DEFAULT, FCFS, RANDOM, RANDOMPROB; // 랜덤 확률
		// QUIZ // QUIZ는 여기 있으면 안됨 나중에 수정할 수 있도록
	}

	public enum DateType {
		ONCE, ALLTIME, MINUTE, HOUR, DAY, MONTH, YEAR;
	}

	public enum EventInfoResultCode {
		SUCCESS("00", "성공적으로 저장되었습니다."), FAILED_NO_EVENTID_INPUT("01", "eventId를 입력해주세요."),
		FAILED_NO_DATE_INPUT("02", "StartDt, EndDt를 입력해주세요 입력 형식은 YYYY-MM-DDT hh:mm:ss 입니다."),
		FAILED_DATE_ORDER("03", "StartDate가 EndDate보다 느립니다."),
		FAILED_NO_OVERLAP_INPUT("04", "overLapDateCount 및 includeDateTF를 입력해 주세요"),
		FAILED_NO_REWARDINFO_INPUT("05", "rewardInfo를 입력해 주세요"),
		FAILED_NO_QUIZANSWER_INPUT("06", "quizAnswer를 입력해 주세요"), FAILED_EVENTID_OVERLAP("07", "이미 등록된 EventID입니다."),
		FAILED_RANDOMPROB_OVER_ONE("08", "RANDOM 확률의 총 합은 1을 넘을 수 없습니다.");

		private String resultCode;
		private String resultMessage;

		private EventInfoResultCode(String resultCode, String resultMessage) {
			this.resultCode = resultCode;
			this.resultMessage = resultMessage;
		}

		public String getResultCode() {
			return resultCode;
		}

		public String getResultMessage() {
			return resultMessage;
		}

		public Boolean isSuccess() {
			if (this.equals(SUCCESS)) {
				return true;
			} else {
				return false;
			}
		}
	}

}
