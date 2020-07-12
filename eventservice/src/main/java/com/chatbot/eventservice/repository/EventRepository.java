package com.chatbot.eventservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatbot.eventservice.dto.Event;




public interface EventRepository  extends MongoRepository<Event, String> {

	public Event[] findByEventId(String eventId); 
	public Event[] findByEventIdAndClnn(String eventId, String clnn);
}
