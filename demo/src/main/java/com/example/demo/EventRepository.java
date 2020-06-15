package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;




public interface EventRepository  extends MongoRepository<Event, String> {

	  public Event findByClnn(String clnn);
	  //public Event findByclnnevent(@Param("clnn") String clnn,@Param("eventId") String eventId);
	  
}
