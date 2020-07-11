package com.chatbot.eventservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.dto.Event;
import com.chatbot.eventservice.dto.EventSetup;
import com.chatbot.eventservice.repository.EventRepository;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private EventSetupRepository eventSetupRepository;
	
	public Event applyEvent(Event inputEvent) {
		
		if(inputEvent.getEventId()==null) {
			inputEvent.setResult("EventId를 입력하세요");
			return inputEvent;
		}
		if(inputEvent.getClnn()==null) {
			inputEvent.setResult("clnn을 입력하세요");
			return inputEvent;
		}
		
		inputEvent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		//EVENTMANAGE DB에서 event 정보 가져오기
		EventSetup findEventSetup = eventSetupRepository.findByEventId(inputEvent.getEventId());
		if(findEventSetup == null)
		{
			inputEvent.setResult("eventId가 DB에 저장되어 있지 않습니다.");
			return inputEvent;
		}
		
		//신청 건수 제한이 있는지? limit을 보고
		if(findEventSetup.getLimit().equals("Y"))
		{
			Event findEventlist[] = eventRepository.findByEventId(inputEvent.getEventId());
			//신청 가능 건수를 넘어간 경우
			if(findEventlist.length >= findEventSetup.getHowManyPeople())
			{
				inputEvent.setResult("신청 가능 건수를 넘겼습니다");
				return inputEvent;
			}
			//여기다가는 rank에 따른 rank값 입력을 해야함.로직 구현 하도록~~
			else
			{
				inputEvent.setLimitCount(findEventlist.length+1);
				inputEvent.setRank(2);
			}
		}
		
		
		return inputEvent;
	}

	
	/*
	public EventResult eventSet(Event event)
	{
		EventResult result = new EventResult();
				
		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
		//System.out.println(findEvent);
		
		if(findEvent != null) // clnn, event로 query 조회 결과가 있는경우
		{
			
			result.setApply("Y");
			result.setDate(findEvent.getDate());
			result.setClnn(findEvent.getClnn());
			result.setEventId(findEvent.getEventId());
			return result;
		}
		else // clnn, event로 query 조회 결과가 없는경우
		{
			event.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			eventRepository.save(event);
			result.setApply("N");
			result.setDate(event.getDate());
			result.setClnn(event.getClnn());
			result.setEventId(event.getEventId());
			return result;
		}
	}
	
	public EventResult eventGet(Event event)
	{
		EventResult result = new EventResult();
		
		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
		//System.out.println(findEvent);
		
		if(findEvent != null) // clnn, event로 query 조회 결과가 있는경우
		{
			
			result.setApply("Y");
			result.setDate(findEvent.getDate());
			result.setClnn(findEvent.getClnn());
			result.setEventId(findEvent.getEventId());
			return result;
		}
		else // clnn, event로 query 조회 결과가 없는경우
		{
			event.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			//eventRepository.save(event);
			result.setApply("N");
			result.setDate(event.getDate());
			result.setClnn(event.getClnn());
			result.setEventId(event.getEventId());
			return result;
		}
	}
	
	public EventResult eventDelete(Event event)
	{
		EventResult result = new EventResult();
		
		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
		//System.out.println(findEvent);
		
		if(findEvent != null) // clnn, event로 query 조회 결과가 있는경우 document 삭제
		{
			eventRepository.deleteById(findEvent.getId());
			result.setApply("Y");
			result.setDate(findEvent.getDate());
			result.setClnn(findEvent.getClnn());
			result.setEventId(findEvent.getEventId());
			return result;
		}
		else // clnn, event로 query 조회 결과가 없는경우
		{
			event.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			//eventRepository.save(event);
			result.setApply("N");
			result.setDate(event.getDate());
			result.setClnn(event.getClnn());
			result.setEventId(event.getEventId());
			return result;
		}
	}	
	
	public EventResult eventTest(Event event)
	{
		EventResult result = new EventResult();
		
		result.setApply("N");
		result.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		result.setClnn(event.getClnn());
		result.setEventId(event.getEventId());	
		
		Event findEvent = eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
		//System.out.println(findEvent);
		if(findEvent != null)
		{
			result.setApply("Y");
			result.setDate(findEvent.getDate());
			return result;
		}
		else
		{
			return result;
		}
	}
	*/
}

