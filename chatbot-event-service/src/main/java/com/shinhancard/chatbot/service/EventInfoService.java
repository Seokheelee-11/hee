package com.shinhancard.chatbot.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.controller.request.EventInfoRequest;
import com.shinhancard.chatbot.controller.response.EventInfoResponse;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.domain.EventInfo.RewardType;
import com.shinhancard.chatbot.domain.ResultCode;
import com.shinhancard.chatbot.repository.EventInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventInfoService {

	private static final Logger log = LoggerFactory.getLogger(EventInfoService.class);
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
		// resultCode setting
		ResultCode eventInfoResultCode = ResultCode.SUCCESS;

		// 입력값 Validation
		if (!getInputValidation(eventInfo)) {
			eventInfoResultCode = ResultCode.FAILED_DEFAULT_INPUT;
		}

		// validation 체크를 통과한 경우 DB에 저장
		if (eventInfoResultCode.isSuccess()) {
			eventInfoRepository.save(eventInfo);
		}
		// Response의 resultCode 채움
		eventInfoResponse.setResult(eventInfoResultCode);

		return eventInfoResponse;
	}

	public Boolean getInputValidation(EventInfo eventInfo) {
		ResultCode resultCode = ResultCode.SUCCESS;
		// EventId가 입력되었는지?
		if (!eventInfo.getValidationEventId()) {
			resultCode = ResultCode.FAILED_NO_EVENTID_INPUT;
		}
		// Date 입력이 정상적으로 입력되었는지?
		if (!eventInfo.getValidationDateInput()) {
			resultCode = ResultCode.FAILED_NO_DATE_INPUT;
		}
		if (!eventInfo.getValidationDateOrder()) {
			resultCode = ResultCode.FAILED_DATE_ORDER;
		}
		// OverLap 입력이 정상적으로 되었는지?
		if (!eventInfo.getValidationOverLapInput()) {
			resultCode = ResultCode.FAILED_NO_OVERLAP_INPUT;
		}
		// rewardInfo 입력이 정상적으로 되었는지?
		if (eventInfo.getRewardTF()) {
			// default로 입력해야 할 값이 정상적으로 입력되었는가?
			if (!eventInfo.getValidationRewardDefaultInput()) {
				resultCode = ResultCode.FAILED_NO_LIMIT_INPUT;
			}
			// RANDOMPROB일 때 확률의 값이 1 이하인가?
			if (eventInfo.getRewardType().equals(RewardType.RANDOMPROB)) {
				if (!eventInfo.getValidationRewardRandomProbInput()) {
					resultCode = ResultCode.FAILED_RANDOMPROB_OVER_ONE;
				}
			}
		}

		// evendId가 중복되었는지?
		if (getValidationEventIdOverLap(eventInfo)) {
			resultCode = ResultCode.FAILED_EVENTID_OVERLAP;
		}

		log.trace("resultCode", resultCode.getResultMessage());

		if (resultCode.isSuccess())
			return true;
		else
			return false;

	}

	public Boolean getValidationEventIdOverLap(EventInfo eventInfo) {
		EventInfo findOneByEventId = eventInfoRepository.findOneByEventId(eventInfo.getEventId());
		if (findOneByEventId == null) {
			return false;
		} else {
			return true;
		}
	}

}
