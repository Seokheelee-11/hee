package com.shinhancard.chatbot.service;

import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.repository.EventHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventHistoryService {
	private final EventHistoryRepository eventHistoryRepository;
	
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

	public void deleteEvent(String id) {
		eventHistoryRepository.deleteById(id);
	}

	public void joinEvent(String eventId) {

	}
	
	
	public EventHistory getEventHistory(String eventId, String clnn) {
		
		//TODO :: 조회 
		return null;
	}
}
