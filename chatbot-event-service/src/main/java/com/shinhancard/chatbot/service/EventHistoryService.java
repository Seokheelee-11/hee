package com.shinhancard.chatbot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.shinhancard.chatbot.controller.request.EventHistoryRequest;
import com.shinhancard.chatbot.controller.response.EventHistoryResponse;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventHistoryLog;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.EventResultCode;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.repository.EventHistoryRepository;
import com.shinhancard.chatbot.repository.EventInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventHistoryService {
	private final EventHistoryRepository eventHistoryRepository;
	private final EventInfoRepository eventInfoRepository;
	
	public EventHistory getEventHistoryById(String id) {
		return eventHistoryRepository.findOneById(id);
	}
	
	public EventHistory updateEventHistory(String id, EventHistory event) {
		EventHistory savedEvent = eventHistoryRepository.findOneById(event.getId());
		savedEvent.update(event);

		// 사실 데이터를 조회 > 수정 하게 되면 JPA에서는 '영속성 관리' 라는 것을 해주기 때문에 save를 따로 해줄필요가 없지만..
		// 그냥 명시적으로 save 추가해 주었음
		return eventHistoryRepository.save(savedEvent);
	}

	public void deleteEventHistory(String id) {
		eventHistoryRepository.deleteById(id);
	}

	public void joinEvent(String eventId) {

	}
	
	
	public EventHistory getEventHistory(String eventId, String clnn) {
		
		//TODO :: 조회 
		return null;
	}
	
	public EventHistoryResponse registEventHistory(EventHistoryRequest eventHistoryRequest) {
		ModelMapper modelMapper = new ModelMapper();		
		EventHistory eventHistory = modelMapper.map(eventHistoryRequest, EventHistory.class);
		EventHistoryResponse eventHistoryResponse = modelMapper.map(eventHistory, EventHistoryResponse.class);
		EventHistoryLog eventHistoryLog = new EventHistoryLog();
		
		
		ResultCode eventHistoryResultCode = ResultCode.SUCCESS;
		
		//기본 validation check
		if (!eventHistory.getValidationEventIdInput()) {
			eventHistoryResultCode = ResultCode.FAILED_NO_EVENTID_INPUT;
		}
		if (!eventHistory.getValidationClnnInput()) {
			eventHistoryResultCode = ResultCode.FAILED_NO_CLNN_INPUT;
		}		
			
		EventInfo findEventInfo = eventInfoRepository.findOneByEventId(eventHistory.getEventId());
		//findEventInfo Validation
		if (!getEventInfoExistence(findEventInfo)) {
			eventHistoryResultCode = ResultCode.FAILED_CANT_FIND_EVENTID;
		}
		
		
		//applyDate Validation
		if(eventHistoryResultCode.isSuccess()) {
			if(findEventInfo.getEventDateValidate(eventHistoryLog.getRegDate())) {
				eventHistoryResultCode = ResultCode.FAILED_NO_APPLY_DATE;
			}
		}
		
		EventHistory findEventIdAndClnn = eventHistoryRepository
										.findOneByEventIdAndClnn
										(eventHistory.getEventId(), eventHistory.getClnn());
		
		
		//EventIdandClnn으로 값을 찾으면 eventHistory domain객체의 값을 찾은 값으로 바꿈
		if (eventHistoryResultCode.isSuccess()
			&& getEventHistoryExistence(findEventIdAndClnn)) {
			eventHistory = findEventIdAndClnn;
		}
		
		//Event 신청 내역이 존재하는데, overLap이 False인 경우 "이미 이벤트에 신청한 것"
		if(eventHistoryResultCode.isSuccess()
			&& getEventHistoryExistence(findEventIdAndClnn)
			&& !findEventInfo.getOverLapTF()) {
			eventHistoryResultCode = ResultCode.FAILED_ALREADY_APPLIED;
		}
		
		//OverLap에 따른 이벤트 신청이 가능한지 파악함
		if(eventHistoryResultCode.isSuccess()
				&& findEventInfo.needOverLapLogics()) {
				//TODO:: 아직 로직 추가 안함
			}
		
		
		
			
		return eventHistoryResponse;		
	}
	
	
	public Boolean getEventInfoExistence(EventInfo findEventInfo) {
		if (findEventInfo == null) {
			return false;
		}
		return true;
	}
	
	public Boolean getEventHistoryExistence(EventHistory findEventHistory) {
		if (findEventHistory == null) {
			return false;
		}
		return true;
	}
	


}
