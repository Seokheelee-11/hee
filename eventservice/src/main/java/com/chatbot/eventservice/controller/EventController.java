package com.chatbot.eventservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.eventservice.dto.EventInputDto;
import com.chatbot.eventservice.dto.EventOutputDto;
import com.chatbot.eventservice.service.EventService;

@RestController
public class EventController {

	@Autowired
	private EventService eventService;
	
	@ResponseBody
	@RequestMapping(value="/applyEvent", method=RequestMethod.POST)
	public EventOutputDto eventApply(@RequestBody EventInputDto eventInputDto) {
		return eventService.applyEvent(eventInputDto);
	}
}