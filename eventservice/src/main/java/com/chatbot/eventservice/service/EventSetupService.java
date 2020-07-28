package com.chatbot.eventservice.service;

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
	
	
	private final String ERRROR_EVENT_ID ="eventId를 입력해주세요";
	private final String ERRROR_START_DATE ="eventId를 입력해주세요";
	private final String ERRROR_END_DATE ="eventId를 입력해주세요";
	
	private int getEventTotalCount(,  , )
	{
		for (String key : keys) {
			//System.out.println(key);
			totalCount += eventSetup.getRewardInfo().get(key);
		}
	}
	
	public EventSetupOutputDto eventSet2(EventSetupInputDto eventSetupInputDto)
	{
		ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		modelMapper.addMappings(modelMapperConfig.EventSetupInputToEventMap);
		EventSetup eventSetup = modelMapper.map(eventSetupInputDto, EventSetup.class);
		EventSetupOutputDto eventSetupOutputDto = new EventSetupOutputDto();

		
		// ------------ Check ------------------------
		if (eventSetup.getEventId() == null) {
			eventSetupOutputDto.setResponseMessage(ERRROR_EVENT_ID);
			return eventSetupOutputDto;
		}
		System.out.println("시작날짜, 종료날짜 입력 체크");
		// 시작날짜, 종료날짜 입력 체크
		if (eventSetup.getStartDate() == null || eventSetup.getEndDate() == null) {
			eventSetupOutputDto.setResponseMessage("StartDate, EndDate에 값을 입력해주세요... ");
			return eventSetupOutputDto;
		}
		
		System.out.println("시작날짜 < 종료날짜 인지 체크");
		//시작날짜 < 종료날짜 인지 체크
		if(eventSetup.getStartDate().isAfter(eventSetup.getEndDate())) {
			eventSetupOutputDto.setResponseMessage("StartDate가 EndDate보다 늦습니다. ");
			return eventSetupOutputDto;
		}
		
		// 중복체크 
		
		// ------------ Setup -------------------------
		System.out.println("추첨 혹은 선착순 일때 total count 입력");
		// 추첨 or 선착순 일때 total count 입력
		int totalCount = 0;
		Set<String> keys = eventSetup.getRewardInfo().keySet();

		totalCount = getEventTotalCout(   ??? );
		
		if(RewardType.FCFS.equals(eventSetup.getRewardType()) ||RewardType.RANDOM.equals(eventSetup.getRewardType()))
		{
			eventSetup.setTotalCount(totalCount);
		}
		else if(RewardType.RANDOM_PROB.equals(eventSetup.getRewardType())) {
			if(totalCount >1) {
				eventSetupOutputDto.setResponseMessage("RANDOM 확률의 총 합은 1을 넘을 수 없습니다. 현재 총합 : " + totalCount);
				return eventSetupOutputDto;
			}
		}
		System.out.println(eventSetup);

		eventSetupRepository.save(eventSetup);

		eventSetupOutputDto = modelMapper.map(eventSetup, EventSetupOutputDto.class);
		eventSetupOutputDto.setResultStatus("Y");
		eventSetupOutputDto.setResponseMessage("정상등록 되었음");
		System.out.println(eventSetupOutputDto);

		return eventSetupOutputDto;
		
		return eventSetupOutputDto;
	}

	public EventSetupOutputDto eventSet(EventSetupInputDto eventSetupInputDto) {

		
 		ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		modelMapper.addMappings(modelMapperConfig.EventSetupInputToEventMap);
		EventSetup eventSetup = modelMapper.map(eventSetupInputDto, EventSetup.class);
		EventSetupOutputDto eventSetupOutputDto = new EventSetupOutputDto();


		if (eventSetup.getEventId() == null) {
			eventSetupOutpggutDto.setResponseMessage("eventId를 입력해주세요");
			return eventSetupOutputDto;
		}
		System.out.println("시작날짜, 종료날짜 입력 체크");
		// 시작날짜, 종료날짜 입력 체크
		if (eventSetup.getStartDate() == null || eventSetup.getEndDate() == null) {
			eventSetupOutputDto.setResponseMessage("StartDate, EndDate에 값을 입력해주세요... ");
			return eventSetupOutputDto;
		}
		System.out.println("시작날짜 < 종료날짜 인지 체크");
		//시작날짜 < 종료날짜 인지 체크
		if(eventSetup.getStartDate().isAfter(eventSetup.getEndDate())) {
			eventSetupOutputDto.setResponseMessage("StartDate가 EndDate보다 늦습니다. ");
			return eventSetupOutputDto;
		}
		
				
		System.out.println("EventId로 DB 조회");
		//EventId로 DB 조회
		EventSetup findEvent = eventSetupRepository.findOneByEventId(eventSetup.getEventId());		
		
		System.out.println("이벤트 중복 체크");
		// 이벤트 중복 체크
		if (findEvent != null) {
			eventSetupOutputDto.setResponseMessage("eventId 중복");
			return eventSetupOutputDto;
		}
		
		System.out.println("추첨 혹은 선착순 일때 total count 입력");
		// 추첨 or 선착순 일때 total count 입력
		if(RewardType.FCFS.equals(eventSetup.getRewardType()) ||RewardType.RANDOM.equals(eventSetup.getRewardType()))
		{
			int totalCount = 0;
			Set<String> keys = eventSetup.getRewardInfo().keySet();

			for (String key : keys) {
				//System.out.println(key);
				totalCount += eventSetup.getRewardInfo().get(key);
			}
			eventSetup.setTotalCount(totalCount);
		}
		else if(RewardType.RANDOMPROB.equals(eventSetup.getRewardType())) {
			Double totalCount = 0.0;
			Set<String> keys = eventSetup.getRewardInfo().keySet();

			for (String key : keys) {
				//System.out.println(key);
				totalCount += eventSetup.getRewardInfo().get(key);
			}
			if(totalCount >1) {
				eventSetupOutputDto.setResponseMessage("RANDOM 확률의 총 합은 1을 넘을 수 없습니다. 현재 총합 : " + totalCount);
				return eventSetupOutputDto;
			}
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
