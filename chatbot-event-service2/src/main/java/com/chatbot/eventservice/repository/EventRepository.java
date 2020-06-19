package com.chatbot.eventservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatbot.eventservice.dto.ClnnInfo;




public interface EventRepository  extends MongoRepository<ClnnInfo, String> {

	  //public Event findByClnn(String clnn);
	  //public List<ClnnInfo> findByClnn(String clnn);
	  public ClnnInfo findByClnnAndEvent(String clnn,Event event.findByEvent(eventId));
	  
}
