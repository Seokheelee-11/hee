package com.chatbot.eventservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatbot.eventservice.dto.EventSetup;

public interface EventSetupRepository extends MongoRepository<EventSetup,String>{
	
	public EventSetup findByEventId(String eventId);

	
	
}
