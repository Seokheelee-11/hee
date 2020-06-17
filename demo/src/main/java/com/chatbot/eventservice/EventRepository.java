package com.chatbot.eventservice;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;




public interface EventRepository  extends MongoRepository<Event, String> {

	  //public Event findByClnn(String clnn);
	  public List<Event> findByClnn(String clnn);
	  public Event findByClnnAndEventId(String clnn,String eventId);
	  
}
