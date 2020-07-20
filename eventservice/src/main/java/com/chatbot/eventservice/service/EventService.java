package com.chatbot.eventservice.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.domain.Event;
import com.chatbot.eventservice.domain.Event.History;
import com.chatbot.eventservice.domain.EventSetup;
import com.chatbot.eventservice.domain.EventSetup.RewardType;
import com.chatbot.eventservice.dto.EventInputDto;
import com.chatbot.eventservice.dto.EventOutputDto;
import com.chatbot.eventservice.repository.EventRepository;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private EventSetupRepository eventSetupRepository;
	

	public EventOutputDto applyEvent(EventInputDto eventInputDto) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Event event = modelMapper.map(eventInputDto, Event.class);
		EventOutputDto eventOutputDto = new EventOutputDto();

		History inputHistory = new History();
		inputHistory.setDate(LocalDateTime.now());

		// EventID 입력 됨?
		if (event.getEventId() == null) {
			eventOutputDto.setResponseMessage("EventId를 입력하세요");
			return eventOutputDto;
		}

		// clnn 입력 됨?
		if (event.getClnn() == null) {
			eventOutputDto.setResponseMessage("clnn을 입력하세요");
			return eventOutputDto;
		}

		// EventSetup값 DB에서 불러오기
		EventSetup findEventSetup = eventSetupRepository.findByEventId(event.getEventId());

		// EventSetup된 것이 없으면 return
		if (findEventSetup == null) {
			eventOutputDto.setResponseMessage("eventId가 DB에 저장되어 있지 않습니다.");
			return eventOutputDto;
		}

		// 신청가능 기간인지 확인
//		if (findEventSetup.getStartDate()) > Integer.parseInt(inputEvent.getDate())
//				|| Integer.parseInt(findEventSetup.getEndDate()) < Integer.parseInt(inputEvent.getDate())) {
//			inputEvent.setResult("event 신청가능 기간이 아닙니다.");
//			return inputEvent;
//		}

		// eventId and Clnn 조회
		Event findEventIdandClnn = eventRepository.findByEventIdAndClnn(event.getEventId(), event.getClnn());
		
		if (findEventIdandClnn != null) {
			switch (findEventSetup.getDateType()) {
			// DEFAULT인 경우 더이상 신청 불가한 상황임
			case DEFAULT: {
				eventOutputDto.setResponseMessage("이미 이벤트에 신청되었습니다");
				return eventOutputDto;
			}
			// MIN~ YEAR 까지 dateType에 따른 결과값 낼 수 있도록 하기
			case MIN:
			case HOUR:
			case DAY:
			case MONTH:
			case YEAR: {

			}
			// ALL 인 경우 언제든 신청 가능함.
			case ALL:{
				
			}
			
			}
		}

		// rewardType이 default가 아닌 경우
		if (!RewardType.DEFAULT.equals(findEventSetup.getRewardType())) {
			if ("N".equals(findEventSetup.getClosingStatus())) {
				// reward 구하는 로직 구현 임시로 "starbucks"
				inputHistory.setRewardName("starbucks");
								
				Event[] findEventId = eventRepository.findByEventId(event.getEventId());
				// eventId로 조회 결과가 없는 경우
				if (findEventId == null) {
					if (findEventSetup.getTotalCount() <= 1) {
						findEventSetup.setClosingStatus("Y");
					}
				}
				// eventId로 조회 결과가 있는 경우 total count 비교해서 closestatus 상태 변경
				else {
					//event Id로 신청된 건수를 전부 가져옴
					int totalCount = 0;
					for (int i = 0; i < findEventId.length; i++) {
						totalCount += findEventId[i].getTotalOrderCount();
					}
					
					//이번 신청건을 집어넣었을 때, total count와 같아 진다면 더이상 사용 불가하므로 closing status를 Y로 바꿈
					if(totalCount + 1 >=findEventSetup.getTotalCount()) {
						findEventSetup.setClosingStatus("Y");
					}
					
				}
			} 
			else {

				eventOutputDto.setResponseMessage("신청 가능 건수를 초과하였습니다.");
				return eventOutputDto;
			}
		} else {
			inputHistory.setRewardName("DEFAULT");
		}
		
		int length = findEventIdandClnn.historyLogLength()+1;
		System.out.println(length);
		inputHistory.setOrderCount(length);
		//System.out.println(event);
		System.out.println(findEventIdandClnn);
		//System.out.println(findEventSetup);
		System.out.println(inputHistory);
		findEventIdandClnn.historyLogAdd(inputHistory);
		findEventIdandClnn.setTotalOrderCount(inputHistory.getOrderCount());
		findEventIdandClnn.setLastModDate(inputHistory.getDate());
		
		eventRepository.save(findEventIdandClnn);

		eventOutputDto = modelMapper.map(event, EventOutputDto.class);
	
		//eventmanage의 resultinfo 의 key값에 대응하는 결과 가져오기
		if(findEventSetup.getResultInfo().containsKey(inputHistory.getRewardName())) {
			eventOutputDto
			.setResultInfo(
					findEventSetup
					.getResultInfo()
					.get(inputHistory.getRewardName())
					);
		}
		eventOutputDto.setResponseMessage("정상등록 되었음");
		eventOutputDto.setResultStatus("Y");
	
		return eventOutputDto;
	}
}
		
		// 신청 건수 제한이 있는지? limit을 보고
//		if (findEventSetup.getHowManyLots() > 0) {
//			Event findEventList[] = eventRepository.findByEventId(inputEvent.getEventId());
//			// 신청 가능 건수를 넘어간 경우
//			if (findEventList.length >= findEventSetup.getHowManyLots()) {
//				inputEvent.setResult("신청 가능 건수를 넘겼습니다");
//				return inputEvent;
//			}
//			// 여기다가는 rank에 따른 rank값 입력을 해야함.로직 구현 하도록~~
//			else {
//				inputEvent.setLimitCount(findEventList.length + 1);
//				inputEvent.setRank(2);
//			}
//		}
//
//		Event findEventIdandClnnList[] = eventRepository.findByEventIdAndClnn(inputEvent.getEventId(),
//				inputEvent.getClnn());

		// 중복신청 가능한지 여부
		// "Y".equals(findEventId
//		if (findEventSetup.getOverLap().equals("Y")) {
//			// Arrays.sort(findEventIdandClnnList);
//			/*
//			 * for(int i = 0; i < findEventIdandClnnList.length; i++) {
//			 * System.out.println(findEventIdandClnnList[i].getDate()); }
//			 */
//
//			// dateType별 비교하여 입력이 가능한지 여부 판단하기
//			Event tempEvent;
//			// date로 sorting함
//
//			/*
//			 * for (int i = 0; i < findEventIdandClnnList.length - 1; i++) { for (int j = i
//			 * + 1; j < findEventIdandClnnList.length; j++) { if
//			 * (Integer.parseInt(findEventIdandClnnList[i].getDate()) > Integer
//			 * .parseInt(findEventIdandClnnList[j].getDate())) { tempEvent =
//			 * findEventIdandClnnList[i]; findEventIdandClnnList[i] =
//			 * findEventIdandClnnList[j]; findEventIdandClnnList[j] = tempEvent; } } }
//			 */
//			// sorting결과 보기
//			for (int i = 0; i < findEventIdandClnnList.length; i++) {
//				System.out.println(findEventIdandClnnList[i].getDate());
//			}
//
//		}
		// 중복신청 불가능한 경우
//		else {
//			if (findEventIdandClnnList.length >= 1) {
//				inputEvent.setResult("이미 신청하였습니다.");
//				return inputEvent;
//			}
//		}

		// inputEvent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));


//	
//	public EventResult eventSet(Event event) {
//		EventResult result = new EventResult();
//
//		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(), event.getEventId());
//		// System.out.println(findEvent);
//
//		if (findEvent != null) // clnn, event로 query 조회 결과가 있는경우 {
//
//			result.setApply("Y");
//		result.setDate(findEvent.getDate());
//		result.setClnn(findEvent.getClnn());
//		result.setEventId(findEvent.getEventId());
//		return result;
//	}else // clnn,
//
//	event로 query
//	조회 결과가 없는경우
//	{
//		event.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//		eventRepository.save(event);
//		result.setApply("N");
//		result.setDate(event.getDate());
//		result.setClnn(event.getClnn());
//		result.setEventId(event.getEventId());
//		return result;
//	}
//	}
//
//	public EventResult eventGet(Event event) {
//		EventResult result = new EventResult();
//
//		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(), event.getEventId());
//		// System.out.println(findEvent);
//
//		if (findEvent != null) // clnn, event로 query 조회 결과가 있는경우 {
//
//			result.setApply("Y");
//		result.setDate(findEvent.getDate());
//		result.setClnn(findEvent.getClnn());
//		result.setEventId(findEvent.getEventId());
//		return result;
//	}else // clnn,
//
//	event로 query
//	조회 결과가 없는경우
//	{
//		event.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // eventRepository.save(event);
//		result.setApply("N");
//		result.setDate(event.getDate());
//		result.setClnn(event.getClnn());
//		result.setEventId(event.getEventId());
//		return result;
//	}
//	}
//
//	public EventResult eventDelete(Event event) {
//		EventResult result = new EventResult();
//
//		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(), event.getEventId());
//		// System.out.println(findEvent);
//
//		if (findEvent != null) // clnn, event로 query 조회 결과가 있는경우 document 삭제 {
//			eventRepository.deleteById(findEvent.getId());
//		result.setApply("Y");
//		result.setDate(findEvent.getDate());
//		result.setClnn(findEvent.getClnn());
//		result.setEventId(findEvent.getEventId());
//		return result;
//	}else // clnn,
//
//	event로 query
//	조회 결과가 없는경우
//	{
//		event.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // eventRepository.save(event);
//		result.setApply("N");
//		result.setDate(event.getDate());
//		result.setClnn(event.getClnn());
//		result.setEventId(event.getEventId());
//		return result;
//	}
//	}
//
//	public EventResult eventTest(Event event) {
//		EventResult result = new EventResult();
//
//		result.setApply("N");
//		result.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//		result.setClnn(event.getClnn());
//		result.setEventId(event.getEventId());
//
//		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(), event.getEventId());
//		// System.out.println(findEvent); if(findEvent != null) {
//		result.setApply("Y");
//		result.setDate(findEvent.getDate());
//		return result;
//	}else
//
//	{
//		return result;
//	}
//}


