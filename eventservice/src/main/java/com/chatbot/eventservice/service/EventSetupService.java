package com.chatbot.eventservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.dto.EventSetupInputDto;
import com.chatbot.eventservice.dto.EventSetupOutputDto;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventSetupService {

	@Autowired
	private EventSetupRepository eventSetupRepository;

	public EventSetupOutputDto eventSet(EventSetupInputDto eventSetupInputDto) {
		EventSetupOutputDto eventSetupOutputDto = new EventSetupOutputDto();
		return eventSetupOutputDto;
//		if (inputEvent.getEventId() == null) {
//			inputEvent.setResult("eventId를 입력해주세요");
//			return inputEvent;
//		}
//		// 시작날짜, 종료날짜 입력 체크
//		if (inputEvent.getStartDate() == null || inputEvent.getEndDate() == null) {
//			inputEvent.setResult("StartDate, EndDate에 값을 입력해주세요... ");
//			return inputEvent;
//		}
//		
//		// overLap=Y 입력시 dataType check
//		if (inputEvent.getOverLap().equals("Y")) {
//			if (inputEvent.getDateType() == null) {
//				inputEvent.setResult("dateType 입력 필요");
//				return inputEvent;
//			}
//		}
//		
//		if(!inputEvent.getDrawLots().isEmpty() && !inputEvent.getFcfs().isEmpty()) {
//			inputEvent.setResult("DrawLots(추첨)과 fcfs(선착순)은 동시에 진행 불가");
//			return inputEvent;
//		}
//		
//		// Lots에 값이 있는지 확인 후 있으면 howManyLots에 셋팅
//		if (inputEvent.getDrawLots() != null) {
//			int howMany = 0;
//			Set<String> lotskeys = inputEvent.getDrawLots().keySet();
//
//			for (String key : lotskeys) {
//				//System.out.println(key);
//				howMany += inputEvent.getDrawLots().get(key);
//			}
//			inputEvent.setHowManyLots(howMany);
//		}
//		
//		// fcfs에 값이 있는지 확인 후 있으면 howManyFcfs에 셋팅
//		if(inputEvent.getFcfs() != null) {
//			int howMany = 0;
//			Set<String> fcfsKeys = inputEvent.getFcfs().keySet();
//
//			for (String key : fcfsKeys) {
//				//System.out.println(key);
//				howMany += inputEvent.getFcfs().get(key);
//			}
//			inputEvent.setHowManyFcfs(howMany);
//		}
//		
//		
//		
//
//
//		EventSetup findEvent = eventSetupRepository.findByEventId(inputEvent.getEventId());
//
//		// 이벤트 중복 체크
//		if (findEvent != null) {
//			inputEvent.setResult("eventId 중복");
//			inputEvent.setDate(findEvent.getDate());
//			inputEvent.setEventId(findEvent.getEventId());
//			return inputEvent;
//		}
//
//		inputEvent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//		inputEvent.setApply("Y");
//		inputEvent.setResult("정상등록 되었음");
//
//		eventSetupRepository.save(inputEvent);
	

	}
}
