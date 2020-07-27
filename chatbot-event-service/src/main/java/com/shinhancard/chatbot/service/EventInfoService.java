package com.shinhancard.chatbot.service;

import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.repository.EventInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventInfoService {

	private final EventInfoRepository eventRepository;
	
	public EventInfo getEventById(String id) {
		return eventRepository.findOneById(id);
	}
	
	public EventInfo registEvent(EventInfo event) {
		return eventRepository.save(event);
	}
	
	public EventInfo updateEvent(String id, EventInfo event) {
		EventInfo savedEvent = eventRepository.findOneById(event.getId());
		savedEvent.update(event);
		
		//사실 데이터를 조회 > 수정 하게 되면 JPA에서는 '영속성 관리' 라는 것을 해주기 때문에 save를 따로 해줄필요가 없지만..
		//그냥 명시적으로 save 추가해 주었음 
		return eventRepository.save(savedEvent);
	}
	
	public void deleteEvent(String id) {
		eventRepository.deleteById(id);
	}
	
	public void joinEvent(String eventId) {
		
	}
}
