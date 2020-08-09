package com.shinhancard.chatbot.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class EventHistory {
	@Id
	private String id;

	private String eventId;
	private String clnn;
	private String displayName;

	private Integer lastOrder;
	private List<EventHistoryLog> logs;
	// TODO :: 최소한의 필드만 넣어놨으니 더 추가할 것

	private LocalDateTime lastModDt;

	public void addLog(EventHistoryLog log) {
		this.logs.add(log);
	}

	public void update(EventHistory newEvent) {
		this.eventId = newEvent.getEventId();
		this.displayName = newEvent.getDisplayName();

	}

	public void updateLastOrder(int count) {
		this.lastOrder = count;
		this.lastModDt = LocalDateTime.now();
	}

	public EventHistory setEventHistory(EventHistoryLog eventHistoryLog) {
		this.lastOrder = eventHistoryLog.getOrder();
		this.lastModDt = eventHistoryLog.getRegDate();
		this.logs.add(eventHistoryLog);
		return this;
	}
	
	public EventHistoryLog getLastHistory() {
		EventHistoryLog result =this.logs.get(this.logs.size());
		return result;
	}
	
	
	public Boolean canApplyOverLap(EventInfo findEventInfo, EventHistoryLog eventHistoryLog) {
		// include인 경우 로직
		if(findEventInfo.getIncludeDateTF()) {
			if(!canIncludeOverLap(findEventInfo, eventHistoryLog)) {
				return false;
			}
		}
		// include가 아닌 경우 로직
		else {
			if(!canNoIncludeOverLap(findEventInfo, eventHistoryLog)) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean canNoIncludeOverLap(EventInfo findEventInfo, EventHistoryLog eventHistoryLog) {
		if (findEventInfo.getOverLapDateType().isMinute() && eventHistoryLog.getRegDate()
				.isBefore(this.lastModDt.plusMinutes(findEventInfo.getOverLapDateCount()))) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isHour() && eventHistoryLog.getRegDate()
				.isBefore(this.lastModDt.plusHours(findEventInfo.getOverLapDateCount()))) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isDay() && eventHistoryLog.getRegDate()
				.isBefore(this.lastModDt.plusDays(findEventInfo.getOverLapDateCount()))) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isMonth() && eventHistoryLog.getRegDate()
				.isBefore(this.lastModDt.plusMonths(findEventInfo.getOverLapDateCount()))) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isYear() && eventHistoryLog.getRegDate()
				.isBefore(this.lastModDt.plusYears(findEventInfo.getOverLapDateCount()))) {
			return false;
		}
		return true;
	}

	public Boolean canIncludeOverLap(EventInfo findEventInfo, EventHistoryLog eventHistoryLog) {
		if (findEventInfo.getOverLapDateType().isMinute()&& ChronoUnit.MINUTES.between(this.lastModDt,
				eventHistoryLog.getRegDate()) <= findEventInfo.getOverLapDateCount()) {
			return false;
		}else if (findEventInfo.getOverLapDateType().isHour() && ChronoUnit.HOURS.between(this.lastModDt,
				eventHistoryLog.getRegDate()) <= findEventInfo.getOverLapDateCount()) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isDay() && ChronoUnit.DAYS.between(this.lastModDt,
				eventHistoryLog.getRegDate()) <= findEventInfo.getOverLapDateCount()) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isMonth() && ChronoUnit.MONTHS.between(this.lastModDt,
				eventHistoryLog.getRegDate()) <= findEventInfo.getOverLapDateCount()) {
			return false;
		} else if (findEventInfo.getOverLapDateType().isYear() && ChronoUnit.YEARS.between(this.lastModDt,
				eventHistoryLog.getRegDate()) <= findEventInfo.getOverLapDateCount()) {
			return false;
		}
		return true;
	}

	public Boolean getValidationEventIdInput() {
		if (this.eventId.isEmpty()) {
			return false;
		}
		return true;
	}

	public Boolean getValidationClnnInput() {
		if (this.clnn.isEmpty()) {
			return false;
		}
		return true;
	}

}
