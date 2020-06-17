package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EventController {

	@Autowired
	private EventService eventService;
		
	@ResponseBody
	@RequestMapping(value="/setevent", method=RequestMethod.POST)
	public EventResult eventSet(@RequestBody Event event) {
		return eventService.eventSet(event);
	}
	
	@ResponseBody
	@RequestMapping(value="/getevent", method=RequestMethod.POST)
	public EventResult eventGet(@RequestBody Event event) {
		return eventService.eventGet(event);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteevent", method=RequestMethod.POST)
	public EventResult eventDelete(@RequestBody Event event) {
		return eventService.eventDelete(event);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/testevent", method=RequestMethod.POST)
	public EventResult eventTest(@RequestBody Event event) {
		return eventService.eventTest(event);
	}
	

}
