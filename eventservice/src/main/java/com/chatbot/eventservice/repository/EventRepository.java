package com.chatbot.eventservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatbot.eventservice.dao.EventResult;




public interface EventRepository  extends MongoRepository<EventResult, String> {

	  //public Event findByClnn(String clnn);
	  //public List<ClnnInfo> findByClnn(String clnn);
	  //public ClnnInfo findByClnnAndEvent(String clnn,Event event.findByEvent(eventId));
	  
}
