package com.chatbot.eventservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.eventservice.service.EventService;

@RestController
public class EventController {

	@Autowired
	private EventService eventService;
	///dd
	/*
	@ResponseBody
	@RequestMapping(value="/setevent", method=RequestMethod.POST)
	public EventResult eventSet(@RequestBody ClnnInfo clnninfo) {
		return eventService.eventSet(clnninfo);
	}
	*/
	/*
	//clnn 과 eventId를 받아서, query로 조회. DB에 있으면 "apply": "Y", 없으면 DB에 추가 후 "apply" : "N" 
	@ResponseBody
	@RequestMapping(value="/setevent", method=RequestMethod.POST)
	public EventResult eventSet(@RequestBody Event event) {
		return eventService.eventSet(event);
	}
	//clnn 과 eventId를 받아서, query로 조회. DB에 있으면 "apply": "Y", 없으면 "apply" : "N"
	@ResponseBody
	@RequestMapping(value="/getevent", method=RequestMethod.POST)
	public EventResult eventGet(@RequestBody Event event) {
		return eventService.eventGet(event);
	}
	//clnn 과 eventId를 받아서, query로 조회. DB에 있으면 삭제 후 "apply": "Y", 없으면  "apply" : "N"
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
	*/

}