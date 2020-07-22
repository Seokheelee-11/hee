package com.chatbot.eventservice;

import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Configuration;

import com.chatbot.eventservice.domain.Event;
import com.chatbot.eventservice.domain.EventSetup;
import com.chatbot.eventservice.dto.EventOutputDto;
import com.chatbot.eventservice.dto.EventSetupInputDto;

@Configuration
public class ModelMapperConfig {


	public PropertyMap<EventSetupInputDto, EventSetup> EventSetupInputToEventMap= new PropertyMap<EventSetupInputDto, EventSetup>() {
		protected void configure() {

			map().setStartDate(source.parsedStartDate());
			map().setEndDate(source.parsedEndDate());
		}
	};


	public PropertyMap<Event, EventOutputDto> eventToEventOutputMap = new PropertyMap<Event, EventOutputDto>() {
		protected void configure() {

			map().setRewardName(source.getHistoryEnd().getRewardName());
			map().setParam(source.getHistoryEnd().getParam());
			map().setOrderCount(source.getHistoryEnd().getOrderCount());
			map().setDate(source.getHistoryEnd().getDate());
			
		}
	};

}
