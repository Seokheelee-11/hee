package com.shinhancard.chatbot.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.domain.EventInfo;

class EventServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(EventServiceTest.class);


	@Mock
	private EventInfoService eventInfoService;
	
	@Mock
	private EventHistoryService eventHistoryService;
	
	private EventService eventService;
	
	private EventInfo eventInfo;
	private EventHistory eventHistory;
	
	@BeforeEach
	void setUp() {
		eventService = new EventService(eventInfoService, eventHistoryService);
		eventInfo = new EventInfo();
		eventHistory = new EventHistory();
	}
	
	@Test
	void validateEventApplyTest() {
		boolean result = eventService.validateEventApply(eventInfo, eventHistory);
		assertEquals(false, result);
	}
}
