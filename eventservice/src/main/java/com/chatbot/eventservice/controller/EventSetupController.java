package com.chatbot.eventservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.eventservice.service.EventSetupService;

@RestController
public class EventSetupController {

	@Autowired
	private EventSetupService eventSetupService;
	
}
