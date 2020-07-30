package com.shinhancard.chatbot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.controller.request.EventInfoRequest;
import com.shinhancard.chatbot.controller.response.EventInfoResponse;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.EventResultCode;
import com.shinhancard.chatbot.repository.EventInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventInfoService {

	private final EventInfoRepository eventInfoRepository;

	public EventInfo getEventById(String id) {
		return eventInfoRepository.findOneById(id);
	}
	
	public EventInfo updateEvent(String id, EventInfo event) {
		EventInfo savedEvent = eventInfoRepository.findOneById(event.getId());
		savedEvent.update(event);

		// 사실 데이터를 조회 > 수정 하게 되면 JPA에서는 '영속성 관리' 라는 것을 해주기 때문에 save를 따로 해줄필요가 없지만..
		// 그냥 명시적으로 save 추가해 주었음
		return eventInfoRepository.save(savedEvent);
	}

	public void deleteEvent(String id) {
		eventInfoRepository.deleteById(id);
	}

	public void joinEvent(String eventId) {

	}

	public EventInfoResponse registEvent(EventInfoRequest eventInfoRequest) {
		ModelMapper modelMapper = new ModelMapper();
		EventInfo eventInfo = modelMapper.map(eventInfoRequest, EventInfo.class);
		EventInfoResponse eventInfoResponse = modelMapper.map(eventInfo, EventInfoResponse.class);

		EventResultCode.ResultCode eventInfoResultCode;

		// validation 체크
		eventInfoResultCode = getValidationEventInfo(eventInfo);
		
		//validation 체크를 통과한 경우 DB에 저장
		if(eventInfoResultCode.isSuccess()) {
			eventInfoRepository.save(eventInfo);
		}
		//Response의 resultCode 채움
		eventInfoResponse.setResult(eventInfoResultCode);
	
		return eventInfoResponse;
	}
	

	public EventResultCode.ResultCode getValidationEventInfo(EventInfo eventInfo) {
		EventResultCode.ResultCode result = EventResultCode.ResultCode.SUCCESS;
		//default validation check
		result = eventInfo.getDefaultValidation(result);
		// evendId가 중복되었는지?
		result = getValidationEventIdOverLap(eventInfo, result);
		return result;
	}

	public EventResultCode.ResultCode getValidationEventIdOverLap(EventInfo eventInfo, EventResultCode.ResultCode result) {
		EventInfo findEvent = eventInfoRepository.findOneByEventId(eventInfo.getEventId());
		if (findEvent != null) {
			result = EventResultCode.ResultCode.FAILED_EVENTID_OVERLAP;
		}
		return result;
	}


}
