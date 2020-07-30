package com.shinhancard.chatbot.controller;


import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.controller.request.EventRequest;
import com.shinhancard.chatbot.controller.response.EventResult;
import com.shinhancard.chatbot.service.EventInfoService;
import com.shinhancard.chatbot.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("event")
@RequiredArgsConstructor
public class EventController {

	private final EventInfoService eventInfoService;
	
	private final EventService eventService;
	
	@PostMapping("apply")
	public EventResult joinEvent(@RequestBody @Valid EventRequest params) {
	
		log.info("apply request {}", params.toString());
		//TODO :: joinEvent
		/*
		EventResult result = eventService.joinEvent();
		return modelMapper.map(res, EventResult.class);
		*/
		
		//
		return null;
	}
}
