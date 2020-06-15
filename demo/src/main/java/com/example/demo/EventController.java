package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EventController {

	
	
	@Autowired
	private EventService eventService;
	

	
	@PostMapping("/setevent")
	public String eventSave(@RequestParam(value = "clnn", defaultValue = "P000000000") String clnn, 
							@RequestParam(value = "eventId", defaultValue = "default") String eventId) {
		return eventService.eventSave(clnn,eventId);
	}
	
	@GetMapping("/getevent")
	public String eventGet(@RequestParam(value = "clnn", defaultValue = "P000000000") String clnn, 
							@RequestParam(value = "eventId", defaultValue = "default") String eventId) {
		return eventService.eventGet(clnn,eventId);
	}
	
}
