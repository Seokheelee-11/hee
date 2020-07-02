package com.chatbot.eventservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.eventservice.dto.EventSetup;
import com.chatbot.eventservice.service.EventSetupService;

@RestController
public class EventSetupController {

	@Autowired
	private EventSetupService eventSetupService;
	
	
	@ResponseBody
	@RequestMapping(value="/eventSetup", method=RequestMethod.POST)
	public EventSetup eventSetup(@RequestBody EventSetup eventSetup) {
		
		return eventSetupService.eventSet(eventSetup);
	}
	
}
