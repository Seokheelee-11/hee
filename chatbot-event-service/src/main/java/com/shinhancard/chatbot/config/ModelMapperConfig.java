package com.shinhancard.chatbot.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				   .setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
	
//	public PropertyMap<EventHistory, EventHistoryResponse> eventHistoryToResponse = new PropertyMap<EventHistory, EventHistoryResponse>() {
//		protected void configure() {
//
////			map().setRewardName(source.getHistoryEnd().getRewardName());
////			map().setParam(source.getHistoryEnd().getParam());
////			map().setOrderCount(source.getHistoryEnd().getOrderCount());
////			map().setDate(source.getHistoryEnd().getDate());
//			
//		}
//	};

}
