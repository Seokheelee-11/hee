package com.chatbot.eventservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.ModelMapperConfig;
import com.chatbot.eventservice.domain.EventSetup;
import com.chatbot.eventservice.domain.EventSetup.RewardType;
import com.chatbot.eventservice.dto.EventSetupInputDto;
import com.chatbot.eventservice.dto.EventSetupOutputDto;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventSetupService {

	@Autowired
	private EventSetupRepository eventSetupRepository;
	
	@Autowired
	private ModelMapperConfig modelMapperConfig;

	public EventSetupOutputDto eventSet(EventSetupInputDto eventSetupInputDto) {

		ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		LocalDateTime startdateformat = LocalDateTime.parse(eventSetupInputDto.getStartDate());
		System.out.println(eventSetupInputDto.getStartDate());
		System.out.println(startdateformat);
		
		
		EventSetup eventSetup = modelMapper.map(eventSetupInputDto, EventSetup.class);
		EventSetupOutputDto eventSetupOutputDto = new EventSetupOutputDto();


		if (eventSetup.getEventId() == null) {
			eventSetupOutputDto.setResponseMessage("eventId를 입력해주세요");
			return eventSetupOutputDto;
		}
		System.out.println("시작날짜, 종료날짜 입력 체크");
		// 시작날짜, 종료날짜 입력 체크
		if (eventSetup.getStartDate() == null || eventSetup.getEndDate() == null) {
			eventSetupOutputDto.setResponseMessage("StartDate, EndDate에 값을 입력해주세요... ");
			return eventSetupOutputDto;
		}
		System.out.println("시작날짜, 종료날짜 입력 포맷 체크 구현");
		
		//시작날짜, 종료날짜 입력 포맷 체크 구현
		System.out.println(eventSetup);
		
		
		
		System.out.println("EventId로 DB 조회");
		//EventId로 DB 조회
		EventSetup findEvent = eventSetupRepository.findByEventId(eventSetup.getEventId());		
		
		System.out.println("이벤트 중복 체크");
		// 이벤트 중복 체크
		if (findEvent != null) {
			eventSetupOutputDto.setResponseMessage("eventId 중복");
			return eventSetupOutputDto;
		}
		
		System.out.println("추첨 혹은 선착순 일때 total count 입력");
		// 추첨 or 선착순 일때 total count 입력
		if(eventSetup.getRewardType()!=RewardType.DEFAULT)
		{
			int totalCount = 0;
			Set<String> keys = eventSetup.getRewardInfo().keySet();

			for (String key : keys) {
				//System.out.println(key);
				totalCount += eventSetup.getRewardInfo().get(key);
			}
			eventSetup.setTotalCount(totalCount);
		}
		System.out.println(eventSetup);
//		eventSetup.setDate(LocalDateTime.now());
//		inputEvent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		eventSetupRepository.save(eventSetup);

		eventSetupOutputDto = modelMapper.map(eventSetup, EventSetupOutputDto.class);
		eventSetupOutputDto.setResultStatus("Y");
		eventSetupOutputDto.setResponseMessage("정상등록 되었음");
		System.out.println(eventSetupOutputDto);
		return eventSetupOutputDto;

	}
}
