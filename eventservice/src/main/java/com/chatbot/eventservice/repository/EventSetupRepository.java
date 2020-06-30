package com.chatbot.eventservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatbot.eventservice.dao.EventSetup;
import com.chatbot.eventservice.dao.EventSetupResult;

public interface EventSetupRepository extends MongoRepository<EventSetupResult,String>{
	
	public EventSetup findByEventId(String eventId);
	
}
