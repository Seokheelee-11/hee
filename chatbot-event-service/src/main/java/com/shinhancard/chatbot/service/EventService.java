package com.shinhancard.chatbot.service;

import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.controller.request.EventRequest;
import com.shinhancard.chatbot.controller.result.EventResult;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.HistoryLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

	private final EventInfoService eventInfoService;
	private final EventHistoryService eventHistoryService;
	
	//TODO 얘네는 일단 final 변수로 선언해서 message 정의해두고 나중에 정리해서 파일로 따로 빼두는것이 좋을 듯.
	
	private final String INVALID_EVENT = "invalid event";
	private final String DUPLICATE_PARTICIPATION = "already participated";
	
	public EventResult applyEvent(EventRequest request) {
		
		String eventId = request.getEventId();
		String clnn = request.getClnn();
		
		String resultMessage = INVALID_EVENT;
		
		boolean isValidForApplyEvent = true;
		//event 가져오기 
		EventInfo eventInfo = eventInfoService.getEventById(eventId);
		
		if (eventInfo == null) {
			return new EventResult(resultMessage);
		}
		
		if (eventInfo.isNotValid()) {
			return new EventResult(resultMessage);
		}
		
		//event history 가져오기 
		EventHistory eventHistory = eventHistoryService.getEventHistory(eventId, clnn);
		
		if (eventHistory != null ) {
			
			resultMessage = DUPLICATE_PARTICIPATION;
			isValidForApplyEvent = validateEventApply(eventInfo, eventHistory);
		}
		
		//신청 가능 상태이면 
		if (isValidForApplyEvent) {
			log.info("참여 가능");
			
			eventHistory = executeEvent(eventInfo, eventHistory);
		}
		
		//TODO :: eventHistory 저장 
		
		//TODO :: even 결과로 부터 result 만들어 주고 return
		return null;
	}

	public boolean validateEventApply(EventInfo eventInfo, EventHistory eventHistory) {
		// TODO  eventInfo type, eventHistory 에 따라 참여 가능한지, 불가능한지 로직 처리 
		
		// 신청 가능하면 true, 불가능하면 false 반환 
		return true;
	}
	
	public EventHistory executeEvent(EventInfo eventInfo, EventHistory eventHistory) {
		
		HistoryLog log = new HistoryLog();
		//FCFS, RANDOM 의 경
		if (eventInfo.isFCFS() && eventInfo.isRandom()) {
			//TODO :: event 신청하는 함수 실행 
			//log = 
		}
		
		else if (eventInfo.isRandomProb()) {
			//TODO :: event 신청하는 함수 실행 
			//log = 
		}
		
		//TODO :: 모든 케이스문 통과 후 eventHistory update
		eventHistory.addLog(log);
		eventHistory.updateOrderCount(0);
		
		return eventHistory;
	}
	
	
}
