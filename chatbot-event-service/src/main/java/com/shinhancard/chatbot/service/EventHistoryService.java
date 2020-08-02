package com.shinhancard.chatbot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.controller.request.EventHistoryRequest;
import com.shinhancard.chatbot.controller.response.EventHistoryResponse;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventHistoryLog;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.EventResultCode;
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
		EventResultCode.ResultCode eventHistoryResultCode = EventResultCode.ResultCode.SUCCESS;
		
		//기본 validation check
		eventHistoryResultCode = eventHistory.getValidationEventHistory(eventHistoryResultCode);
		
		EventInfo findEventInfo = eventInfoRepository.findOneByEventId(eventHistory.getEventId());
		//findEventInfo Validation
		eventHistoryResultCode = getEventInfoValidation(eventHistoryResultCode,findEventInfo);
		
		//applyDate Validation
		if(eventHistoryResultCode.equals(EventResultCode.ResultCode.SUCCESS)) {
			eventHistoryResultCode = findEventInfo.getEventDateValidate(eventHistoryResultCode, eventHistoryLog.getRegDate());
		}
		
		EventHistory findEventIdAndClnn = eventHistoryRepository
										.findOneByEventIdAndClnn
										(eventHistory.getEventId(), eventHistory.getClnn());
		
		
		
		return eventHistoryResponse;		
	}
	
	public EventResultCode.ResultCode getEventInfoValidation(EventResultCode.ResultCode result, EventInfo findEventInfo) {
		if (findEventInfo == null) {
			result = EventResultCode.ResultCode.FAILED_CANT_FIND_EVENTID;
		}
		return result;
	}
	


}
