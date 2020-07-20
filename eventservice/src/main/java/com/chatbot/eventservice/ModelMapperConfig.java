package com.chatbot.eventservice;

import org.modelmapper.PropertyMap;

import com.chatbot.eventservice.domain.EventSetup;
import com.chatbot.eventservice.dto.EventSetupInputDto;

public class ModelMapperConfig {

	public class EventSetupInputToEventMap extends PropertyMap<EventSetupInputDto, EventSetup>{
		
		protected void configure() {
			map().setEventId(source.getEventId());
		}
	}
	
}
