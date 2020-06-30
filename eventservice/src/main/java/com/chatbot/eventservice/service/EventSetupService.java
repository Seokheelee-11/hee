package com.chatbot.eventservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.dao.EventSetup;
import com.chatbot.eventservice.dao.EventSetupResult;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventSetupService {

	@Autowired
	private EventSetupRepository eventSetupRepository;
	
	public EventSetupResult eventSet(EventSetup eventSetup)
	{
		EventSetupResult result = new EventSetupResult();
		
		EventSetup findEvent = eventSetupRepository.findByEventId(eventSetup.getEventId());
		
		if(findEvent != null) {			
			result.setResult("eventId 중복");
			result.setApply("N");
			result.setDate(findEvent.getDate());			
			result.setEventId(findEvent.getEventId());
			return result;
		}
		else
		{
			result.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			result.setEventId(eventSetup.getEventId());
			if(eventSetup.getLimit().equals("Y"))
			{
				int howMany;
				howMany = eventSetup.getRank1() + eventSetup.getRank2() + eventSetup.getRank3() + eventSetup.getRank4() + eventSetup.getRank5();
				
				if(howMany <= 0) {
					result.setResult("rank 건수 입력 필요 rank1 ~ rank5까지 입력 가능");
					result.setApply("N");
					return result;
				}
				else {
					result.setHowManyPeople(howMany);
				}
			}
			
			if(eventSetup.getOverLap().equals("Y")) {
				
			}
			
			
			eventSetupRepository.save(result);
			return result;
		}
	}
	
	
}
