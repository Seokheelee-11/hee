package com.shinhancard.chatbot.service;

import org.springframework.stereotype.Service;

import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.repository.EventHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventHistoryService {
	private final EventHistoryRepository eventHistoryRepository;
	
	public EventHistory getEventHistory(String eventId, String clnn) {
		
		//TODO :: 조회 
		return null;
	}
}
