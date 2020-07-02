package com.chatbot.eventservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.dto.EventSetup;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventSetupService {

	@Autowired
	private EventSetupRepository eventSetupRepository;
	
	public EventSetup eventSet(EventSetup inputEvent)
	{		
		EventSetup findEvent = eventSetupRepository.findByEventId(inputEvent.getEventId());
		
		//이벤트 중복 체크
		if(findEvent != null) {			
			inputEvent.setResult("eventId 중복");
			inputEvent.setDate(findEvent.getDate());	
			inputEvent.setEventId(findEvent.getEventId());
			return inputEvent;
		}
		
		inputEvent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));	
		
		//시작날짜, 종료날짜 입력 체크
		if(inputEvent.getStartDate() == null || inputEvent.getEndDate() == null) {
			inputEvent.setResult("StartDate, EndDate에 값을 입력해주세요... ");
			return inputEvent;
		}
			
		if(inputEvent.getLimit().equals("Y"))
		{
			int howMany;
			howMany = inputEvent.getRank1() + inputEvent.getRank2() + inputEvent.getRank3() + inputEvent.getRank4() + inputEvent.getRank5();
			
			if(howMany <= 0) {
				inputEvent.setResult("rank 건수 입력 필요 rank1 ~ rank5까지 입력 가능");
				return inputEvent;
			}
			else {
				inputEvent.setHowManyPeople(howMany);
			}
		}
		
		if(inputEvent.getOverLap().equals("Y")) {
			if(inputEvent.getDateType() == null) {
				inputEvent.setResult("dateType 입력 필요");
				return inputEvent;
			}
		}
				
		inputEvent.setApply("Y");
		inputEvent.setResult("정상등록 되었음");
		
		
		eventSetupRepository.save(inputEvent);
		return inputEvent;
	}
	
	
}
