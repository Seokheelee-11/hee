package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class EventHistory {
	private String id;
	private String eventId;
	private String clnn;
	private int orderCount;
	private List<HistoryLog> logs; 
	//TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것 
	
	private LocalDateTime lastModDt;
	
	public void addLog(HistoryLog log) {
		this.logs.add(log);
	}
	
	public void updateOrderCount(int count) {
		this.orderCount = count;
		this.lastModDt = LocalDateTime.now();
	}
	
	public enum EventHistoryResultCode {
		SUCCESS("00", "성공적으로 저장되었습니다."), 
		FAILED_NO_EVENTID_INPUT("01", "eventId를 입력해주세요."),
		FAILED_NO_CLNN_INPUT("02", "clnn(고객번호)를 입력해주세요"),
		FAILED_CANT_FIND_EVENTID("03", "등록된 EventID가 아닙니다."),
		FAILED_NO_APPLY_DATE("04", "이벤트 신청 기간이 아닙니다."),
		FAILED_ALREADY_APPLIED("05", "이미 이벤트에 신청하셨습니다."),
		FAILED_TIME_OVERLAP("06", "이벤트 신청이 불가합니다. 다음달(내일) 다시 신청해주세요"), 
		FAILED_ORDERCOUNT_OVER("07", "이벤트에 신청 가능한 건수를 초과하였습니다.");

		private String resultCode;
		private String resultMessage;

		private EventHistoryResultCode(String resultCode, String resultMessage) {
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
