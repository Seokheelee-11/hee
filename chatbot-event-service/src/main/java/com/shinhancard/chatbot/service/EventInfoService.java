package com.shinhancard.chatbot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.EventInfo.EventInfoResultCode;
import com.shinhancard.chatbot.dto.EventInfoInput;
import com.shinhancard.chatbot.dto.EventInfoOutput;
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

	public EventInfoOutput registEvent(EventInfoInput eventInfoInput) {
		ModelMapper modelMapper = new ModelMapper();
		EventInfo eventInfo = modelMapper.map(eventInfoInput, EventInfo.class);
		EventInfoOutput eventInfoOutput = modelMapper.map(eventInfo, EventInfoOutput.class);

		EventInfoResultCode eventInfoResultCode;

		// validation 체크
		eventInfoResultCode = getValidationEventInfo(eventInfo);
		
		//validation 체크를 통과한 경우 DB에 저장
		if(eventInfoResultCode.isSuccess()) {
			eventInfoRepository.save(eventInfo);
		}
		
		eventInfoOutput.setResult(eventInfoResultCode);
	
		return eventInfoOutput;
	}
	

	public EventInfoResultCode getValidationEventInfo(EventInfo eventInfo) {
		EventInfoResultCode result = EventInfoResultCode.SUCCESS;
		// EventId가 입력되었는지?
		result = eventInfo.getValidationEventId(result);
		// DateFormat이 정상적으로 입력되었는지?
		result = eventInfo.getValidationDateFormat(result);
		// startDate < EndDate인지?
		result = eventInfo.getValidationDate(result);
		// OverLap이 가능할때 input 값이 제대로 됨?
		result = eventInfo.getValidationOverLapInput(result);
		// randomprob의 값이 1을 넘었는지?
		result = eventInfo.getValidationRandomProbOver(result);
		// rewardInfo 입력이 되었는지?
		result = eventInfo.getValidationRewardInfoInput(result);
		// QuizAnswer 입력이 되었는지?
		result = eventInfo.getValidationQuizAnswerInput(result);
		// evendId가 중복되었는지?
		result = getValidationEventIdOverLap(eventInfo, result);
		return result;
	}

	public EventInfoResultCode getValidationEventIdOverLap(EventInfo eventInfo, EventInfoResultCode result) {
		EventInfo findEvent = eventInfoRepository.findOneByEventId(eventInfo.getEventId());
		if (findEvent != null) {
			result = EventInfoResultCode.FAILED_EVENTID_OVERLAP;
		}
		return result;
	}


}
