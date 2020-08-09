package com.shinhancard.chatbot.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shinhancard.chatbot.controller.request.EventHistoryRequest;
import com.shinhancard.chatbot.controller.response.EventHistoryResponse;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventHistoryLog;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.repository.EventHistoryRepository;
import com.shinhancard.chatbot.repository.EventInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventHistoryService {
	private static final Logger log = LoggerFactory.getLogger(EventHistoryService.class);
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

		EventHistoryLog eventHistoryLog = new EventHistoryLog(eventHistoryRequest);
		EventInfo findEventInfo = eventInfoRepository.findOneByEventId(eventHistory.getEventId());
		EventHistory findEventIdAndClnn = eventHistoryRepository.findOneByEventIdAndClnn(eventHistory.getEventId(),
				eventHistory.getClnn());
		ResultCode eventHistoryResultCode = ResultCode.SUCCESS;

		// 입력값 Validation
		if (!getInputValidation(eventHistory, findEventInfo, eventHistoryLog)) {
			eventHistoryResultCode = ResultCode.FAILED_DEFAULT_INPUT;
		}

		// applyDate Validation
		if (eventHistoryResultCode.isSuccess()) {
			if (!findEventInfo.getEventDateValidate(eventHistoryLog.getRegDate())) {
				eventHistoryResultCode = ResultCode.FAILED_NO_APPLY_DATE;
			}
		}

		// overLap 관련 로직
		if (eventHistoryResultCode.isSuccess() && getEventHistoryExistence(findEventIdAndClnn)) {
			// EventIdandClnn으로 값을 찾으면 eventHistory domain객체의 값을 찾은 값으로 바꿈
			eventHistory = findEventIdAndClnn;
			eventHistoryLog.setOrder(findEventIdAndClnn.getLastOrder() + 1);

			// overLap 신청 가능한지 여부 판단
			if (!getOverLapValidation(eventHistory, findEventInfo, eventHistoryLog)) {
				eventHistoryResultCode = ResultCode.FAILED_OVERLAP_VALIDATE;
			}
		}

		// Reward 관련 로직
		if (eventHistoryResultCode.isSuccess() && findEventInfo.getRewardTF()) {
			List<EventHistory> findEventId = getListEventId(eventHistory.getEventId());
			// reward 처리
			if (!getRewardValidation(findEventInfo, findEventId, eventHistoryLog)) {
				eventHistoryResultCode = ResultCode.FAILED_GET_REWARD;
			} else {
				eventHistoryLog.setRewardName(findEventInfo.getReward(findEventId));
			}
		}

		// validation 체크를 통과한 경우 DB에 저장
		EventHistoryResponse eventHistoryResponse = new EventHistoryResponse();
		if (eventHistoryResultCode.isSuccess()) {
			eventHistory = eventHistory.setEventHistory(eventHistoryLog);
			eventHistoryRepository.save(eventHistory);
			// Response Setting
			eventHistoryResponse = setEventHistoryResponse(eventHistory, eventHistoryResponse, findEventInfo);
		}
		eventHistoryResponse.setResult(eventHistoryResultCode);
		return eventHistoryResponse;
	}

	public EventHistoryResponse setEventHistoryResponse(EventHistory eventHistory,
			EventHistoryResponse eventHistoryResponse, EventInfo findEventInfo) {
		EventHistoryResponse result = new EventHistoryResponse();
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(eventHistoryToResponse);
		eventHistoryResponse = modelMapper.map(eventHistory, EventHistoryResponse.class);
		modelMapper.addMappings(eventInfoToResponse);
		eventHistoryResponse = modelMapper.map(findEventInfo, EventHistoryResponse.class);

		return result;

	}

	public Boolean getOverLapValidation(EventHistory eventHistory, EventInfo findEventInfo,
			EventHistoryLog eventHistoryLog) {
		ResultCode eventHistoryResultCode = ResultCode.SUCCESS;

		// Event 신청 내역이 존재하는데, overLap이 False인 경우 "이미 이벤트에 신청한 것"
		if (!findEventInfo.getOverLapTF()) {
			eventHistoryResultCode = ResultCode.FAILED_ALREADY_APPLIED;
		}
		// OverLap에 따른 이벤트 신청이 가능한지 파악함
		else if (findEventInfo.needOverLapLogics()) {
			if (!eventHistory.canApplyOverLap(findEventInfo, eventHistoryLog)) {
				eventHistoryResultCode = ResultCode.FAILED_TIME_OVERLAP;
			}
		}
		if (eventHistoryResultCode.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean getInputValidation(EventHistory eventHistory, EventInfo findEventInfo,
			EventHistoryLog eventHistoryLog) {
		ResultCode eventHistoryResultCode = ResultCode.SUCCESS;

		// 기본 validation check
		if (!eventHistory.getValidationEventIdInput()) {
			eventHistoryResultCode = ResultCode.FAILED_NO_EVENTID_INPUT;
		}
		if (!eventHistory.getValidationClnnInput()) {
			eventHistoryResultCode = ResultCode.FAILED_NO_CLNN_INPUT;
		}

		// findEventInfo Validation
		if (!getEventInfoExistence(findEventInfo)) {
			eventHistoryResultCode = ResultCode.FAILED_CANT_FIND_EVENTID;
		}

		if (eventHistoryResultCode.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}

	// find한 List의 값이 null이면 걍 선언만 해주고, null이 아니면 List로 전달
	public List<EventHistory> getListEventId(String EventId) {
		List<EventHistory> result = new ArrayList<EventHistory>();

		if (!CollectionUtils.isEmpty(eventHistoryRepository.findAllByEventId(EventId))) {
			result = eventHistoryRepository.findAllByEventId(EventId);
		}
		return result;
	}

	public Boolean getCorrectAnswer(EventInfo findEventInfo, EventHistoryLog eventHistoryLog) {
		if (findEventInfo.getQuizAnswer().equals(eventHistoryLog.getParam())) {
			return true;
		}
		return false;
	}

	public Boolean getRewardValidation(EventInfo findEventInfo, List<EventHistory> findEventId,
			EventHistoryLog eventHistoryLog) {
		if (findEventInfo.getRewardType().isRewardFCFS() || findEventInfo.getRewardType().isRewardRandom()) {
			if (findEventInfo.getTotalCount() > getTotalOrderCount(findEventId)) {
				return false;
			}
		} else if (findEventInfo.getRewardType().isRewardQuiz()) {
			// quiz 정답인지 판단하는 로직
			if (!getCorrectAnswer(findEventInfo, eventHistoryLog)) {
				return false;
			}
		}
		return true;
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

	public Boolean getEventIdExistence(List<EventHistory> findEventId) {
		if (CollectionUtils.isEmpty(findEventId)) {
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

	public PropertyMap<EventHistory, EventHistoryResponse> eventHistoryToResponse = new PropertyMap<EventHistory, EventHistoryResponse>() {
		protected void configure() {
			map().setRewardName(source.getLastHistory().getRewardName());
			map().setParam(source.getLastHistory().getParam());
		}
	};

	public PropertyMap<EventInfo, EventHistoryResponse> eventInfoToResponse = new PropertyMap<EventInfo, EventHistoryResponse>() {
		protected void configure() {
			if (source.getResultInfo().containsKey(map().getRewardName())) {
				map().setResultInfo(source.getResultInfo().get(map().getRewardName()));
				if (source.getResultInfo().get(map().getRewardName()).containsKey("responseMessage")) {
					map().setResponseMessage(source.getResultInfo().get(map().getRewardName()).get("responseMessage"));
				}
			}
		}
	};

}
