package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	
	@Autowired
	private EventRepository eventRepository;
	
	public String eventSave(String clnn, String eventId)
	{
		Event event = new Event();
		event.setClnn(clnn);
		event.setEventId(eventId);
		
		eventRepository.save(event);
		return String.format(
		        "inputEvent[clnn=%s, Event='%s']",
		        clnn, eventId);
	}
	
	public String eventGet(String clnn, String eventId)
	{
		Event event = eventRepository.findByClnn(clnn);
		//Event event = eventRepository.findByclnnevent(clnn,eventId);
		String findClnn = event.getClnn();
		String findEventId = event.getEventId();
		
		return String.format(
		        "getEvent[clnn=%s, Event='%s']",
		        findClnn, findEventId );
	}
	
	
	
}
