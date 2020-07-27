package com.shinhancard.chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	@GetMapping
	public String defaultApi() {
		return "chatbot-event-service";
	}
}
