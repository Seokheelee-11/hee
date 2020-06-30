package com.chatbot.eventservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatbot.eventservice.dao.Event;




public interface EventRepository  extends MongoRepository<Event, String> {

	  //public Event findByClnn(String clnn);
	  //public List<ClnnInfo> findByClnn(String clnn);
	  //public ClnnInfo findByClnnAndEvent(String clnn,Event event.findByEvent(eventId));
	  
}
