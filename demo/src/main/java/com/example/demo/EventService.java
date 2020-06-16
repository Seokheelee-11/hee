package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	
	@Autowired
	private EventRepository eventRepository;
	
	public HashMap<String,String> eventSet(String clnn, String eventId)
	{
		HashMap<String,String> result = new HashMap<>();
		
		result.put("apply","N");
		result.put("Date",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		result.put("clnn",clnn);
		result.put("eventId",eventId);	
		
		Event findEvent = eventRepository.findByClnnAndEventId(clnn,eventId);
		System.out.println(findEvent);
		if(findEvent != null)
		{
			result.put("apply","Y");
			result.put("Date",findEvent.getDate());
			return result;
		}
		else
		{
			Event setevent = new Event();
			setevent.setClnn(clnn);
			setevent.setEventId(eventId);
			setevent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			eventRepository.save(setevent);
			return result;
		}
	}
	
	public HashMap<String,String> eventGet(String clnn, String eventId)
	{
		HashMap<String,String> result = new HashMap<>();
		
		result.put("apply","N");
		result.put("Date",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		result.put("clnn",clnn);
		result.put("eventId",eventId);	
		
		Event findEvent = eventRepository.findByClnnAndEventId(clnn,eventId);
		System.out.println(findEvent);
		if(findEvent != null)
		{
			result.put("apply","Y");
			result.put("Date",findEvent.getDate());
			return result;
		}
		else
		{
			return result;
		}
	}	
}
