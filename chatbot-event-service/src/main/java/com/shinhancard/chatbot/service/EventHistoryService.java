package com.shinhancard.chatbot.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shinhancard.chatbot.controller.request.EventHistoryRequest;
import com.shinhancard.chatbot.controller.response.EventHistoryResponse;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventHistoryLog;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.EventInfo.RewardType;
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

		// TODO :: 조회
		return null;
	}

	public EventHistoryResponse registEventHistory(EventHistoryRequest eventHistoryRequest) {
		ModelMapper modelMapper = new ModelMapper();
		EventHistory eventHistory = modelMapper.map(eventHistoryRequest, EventHistory.class);
		EventHistoryResponse eventHistoryResponse = modelMapper.map(eventHistory, EventHistoryResponse.class);
		EventHistoryLog eventHistoryLog = new EventHistoryLog();
		
		//param이 requst로 입력된 경우 HistoryLog에 param 셋팅
		if (!CollectionUtils.isEmpty(eventHistoryRequest.getParam())) {
			eventHistoryLog.setParam(eventHistoryRequest.getParam());
		}

		ResultCode eventHistoryResultCode = ResultCode.SUCCESS;

		// 기본 validation check
		if (!eventHistory.getValidationEventIdInput()) {
			eventHistoryResultCode = ResultCode.FAILED_NO_EVENTID_INPUT;
		}
		if (!eventHistory.getValidationClnnInput()) {
			eventHistoryResultCode = ResultCode.FAILED_NO_CLNN_INPUT;
		}

		EventInfo findEventInfo = eventInfoRepository.findOneByEventId(eventHistory.getEventId());
		// findEventInfo Validation
		if (!getEventInfoExistence(findEventInfo)) {
			eventHistoryResultCode = ResultCode.FAILED_CANT_FIND_EVENTID;
		}

		// applyDate Validation
		if (eventHistoryResultCode.isSuccess()) {
			if (!findEventInfo.getEventDateValidate(eventHistoryLog.getRegDate())) {
				eventHistoryResultCode = ResultCode.FAILED_NO_APPLY_DATE;
			}
		}

		EventHistory findEventIdAndClnn = eventHistoryRepository.findOneByEventIdAndClnn(eventHistory.getEventId(),
				eventHistory.getClnn());

		//overLap 관련 로직
		if (eventHistoryResultCode.isSuccess() && getEventHistoryExistence(findEventIdAndClnn)) {
			// EventIdandClnn으로 값을 찾으면 eventHistory domain객체의 값을 찾은 값으로 바꿈
			eventHistory = findEventIdAndClnn;
			// Event 신청 내역이 존재하는데, overLap이 False인 경우 "이미 이벤트에 신청한 것"
			if(!findEventInfo.getOverLapTF()) {
				eventHistoryResultCode = ResultCode.FAILED_ALREADY_APPLIED;
			}
			// OverLap에 따른 이벤트 신청이 가능한지 파악함
			else if(findEventInfo.needOverLapLogics()) {
				//TODO:: include인 경우 로직
				if(findEventInfo.getIncludeDateTF()) {
					
				}
				// include가 아닌 경우 로직
				else {
					if(!eventHistory.canNoIncludeOverLap(findEventInfo, eventHistoryLog)) {
						eventHistoryResultCode = ResultCode.FAILED_TIME_OVERLAP;
					}
				}
			}
		}

		// Reward 관련 로직
		if (eventHistoryResultCode.isSuccess() && findEventInfo.getRewardTF()) {
			List<EventHistory> findEventId = eventHistoryRepository.findAllByEventId(eventHistory.getEventId());

			// 신청 가능 건수를 초과하였는지 판단
			if (!findEventInfo.getRewardType().equals(RewardType.RANDOMPROB)) {
				if (!getEventOutOfOrder(findEventInfo, findEventId)) {
					eventHistoryResultCode = ResultCode.FAILED_ORDERCOUNT_OVER;
				}
			}

			// TODO:: Reward 값 구하는 로직 추가
			// eventHistoryLog.setRewardName();

		}

		//Quiz 관련 로직
		if (eventHistoryResultCode.isSuccess() && findEventInfo.getQuizTF()) {
			// quiz 정답인지 판단하는 로직
			if (getCorrectAnswer(findEventInfo, eventHistoryLog)) {
				eventHistoryLog.setRewardName("default");
			}
			else {
				eventHistoryResultCode = ResultCode.FAILED_NO_CORRECT_ANSWER;
			}
		}

		return eventHistoryResponse;
	}


	
	public Boolean getCorrectAnswer(EventInfo findEventInfo, EventHistoryLog eventHistoryLog) {
		if (findEventInfo.getQuizAnswer().equals(eventHistoryLog.getParam())) {
			return true;
		}
		return false;
	}

	public Boolean getEventOutOfOrder(EventInfo findEventInfo, List<EventHistory> findEventId) {
		if (findEventInfo.getTotalCount() > getTotalOrderCount(findEventId)) {
			return true;
		}
		return false;
	}

	public Integer getTotalOrderCount(List<EventHistory> findEventId) {
		Integer result = 0;
		for (int i = 0; i < findEventId.size(); i++) {
			result += findEventId.get(i).getLastOrder();
		}
		return result;
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
