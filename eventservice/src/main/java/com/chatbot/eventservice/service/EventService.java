package com.chatbot.eventservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatbot.eventservice.dto.Event;
import com.chatbot.eventservice.dto.EventSetup;
import com.chatbot.eventservice.repository.EventRepository;
import com.chatbot.eventservice.repository.EventSetupRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private EventSetupRepository eventSetupRepository;

	public Event applyEvent(Event inputEvent) {

		inputEvent.setDate(LocalDateTime.now());
		if (inputEvent.getEventId() == null) {
			inputEvent.setResult("EventId를 입력하세요");
			return inputEvent;
		}
		if (inputEvent.getClnn() == null) {
			inputEvent.setResult("clnn을 입력하세요");
			return inputEvent;
		}

		// EVENTMANAGE DB에서 event 정보 가져오기
		EventSetup findEventSetup = eventSetupRepository.findByEventId(inputEvent.getEventId());
		if (findEventSetup == null) {
			inputEvent.setResult("eventId가 DB에 저장되어 있지 않습니다.");
			return inputEvent;
		}

		String stringtest = "20200444444";
		System.out.println(Integer.parseInt(stringtest.substring(0, stringtest.length())));
		System.out.println(Integer.parseInt(findEventSetup.getStartDate()));

		// 신청가능 기간인지 확인
		/*
		 * if(Integer.parseInt(findEventSetup.getStartDate()) >
		 * Integer.parseInt(inputEvent.getDate()) ||
		 * Integer.parseInt(findEventSetup.getEndDate()) <
		 * Integer.parseInt(inputEvent.getDate())) {
		 * inputEvent.setResult("event 신청가능 기간이 아닙니다."); return inputEvent; }
		 */

		// 신청 건수 제한이 있는지? limit을 보고
		if (findEventSetup.getHowManyLots() > 0) {
			Event findEventList[] = eventRepository.findByEventId(inputEvent.getEventId());
			// 신청 가능 건수를 넘어간 경우
			if (findEventList.length >= findEventSetup.getHowManyLots()) {
				inputEvent.setResult("신청 가능 건수를 넘겼습니다");
				return inputEvent;
			}
			// 여기다가는 rank에 따른 rank값 입력을 해야함.로직 구현 하도록~~
			else {
				inputEvent.setLimitCount(findEventList.length + 1);
				inputEvent.setRank(2);
			}
		}

		Event findEventIdandClnnList[] = eventRepository.findByEventIdAndClnn(inputEvent.getEventId(),
				inputEvent.getClnn());

		// 중복신청 가능한지 여부
		//"Y".equals(findEventId
		if (findEventSetup.getOverLap().equals("Y")) {
			// Arrays.sort(findEventIdandClnnList);
			/*
			 * for(int i = 0; i < findEventIdandClnnList.length; i++) {
			 * System.out.println(findEventIdandClnnList[i].getDate()); }
			 */

			// dateType별 비교하여 입력이 가능한지 여부 판단하기
			Event tempEvent;
			// date로 sorting함

			/*
			 * for (int i = 0; i < findEventIdandClnnList.length - 1; i++) { for (int j = i
			 * + 1; j < findEventIdandClnnList.length; j++) { if
			 * (Integer.parseInt(findEventIdandClnnList[i].getDate()) > Integer
			 * .parseInt(findEventIdandClnnList[j].getDate())) { tempEvent =
			 * findEventIdandClnnList[i]; findEventIdandClnnList[i] =
			 * findEventIdandClnnList[j]; findEventIdandClnnList[j] = tempEvent; } } }
			 */
			// sorting결과 보기
			for (int i = 0; i < findEventIdandClnnList.length; i++) {
				System.out.println(findEventIdandClnnList[i].getDate());
			}

		}
		// 중복신청 불가능한 경우
		else {
			if (findEventIdandClnnList.length >= 1) {
				inputEvent.setResult("이미 신청하였습니다.");
				return inputEvent;
			}
		}

		// inputEvent.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
		inputEvent.setApply("Y");
		inputEvent.setResult("정상등록 되었음");

		eventRepository.save(inputEvent);
		return inputEvent;
	}

	/*
	 * public EventResult eventSet(Event event) { EventResult result = new
	 * EventResult();
	 * 
	 * Event findEvent =
	 * eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
	 * //System.out.println(findEvent);
	 * 
	 * if(findEvent != null) // clnn, event로 query 조회 결과가 있는경우 {
	 * 
	 * result.setApply("Y"); result.setDate(findEvent.getDate());
	 * result.setClnn(findEvent.getClnn());
	 * result.setEventId(findEvent.getEventId()); return result; } else // clnn,
	 * event로 query 조회 결과가 없는경우 {
	 * event.setDate(LocalDateTime.now().format(DateTimeFormatter.
	 * ofPattern("yyyy-MM-dd HH:mm:ss"))); eventRepository.save(event);
	 * result.setApply("N"); result.setDate(event.getDate());
	 * result.setClnn(event.getClnn()); result.setEventId(event.getEventId());
	 * return result; } }
	 * 
	 * public EventResult eventGet(Event event) { EventResult result = new
	 * EventResult();
	 * 
	 * Event findEvent =
	 * eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
	 * //System.out.println(findEvent);
	 * 
	 * if(findEvent != null) // clnn, event로 query 조회 결과가 있는경우 {
	 * 
	 * result.setApply("Y"); result.setDate(findEvent.getDate());
	 * result.setClnn(findEvent.getClnn());
	 * result.setEventId(findEvent.getEventId()); return result; } else // clnn,
	 * event로 query 조회 결과가 없는경우 {
	 * event.setDate(LocalDateTime.now().format(DateTimeFormatter.
	 * ofPattern("yyyy-MM-dd HH:mm:ss"))); //eventRepository.save(event);
	 * result.setApply("N"); result.setDate(event.getDate());
	 * result.setClnn(event.getClnn()); result.setEventId(event.getEventId());
	 * return result; } }
	 * 
	 * public EventResult eventDelete(Event event) { EventResult result = new
	 * EventResult();
	 * 
	 * Event findEvent =
	 * eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
	 * //System.out.println(findEvent);
	 * 
	 * if(findEvent != null) // clnn, event로 query 조회 결과가 있는경우 document 삭제 {
	 * eventRepository.deleteById(findEvent.getId()); result.setApply("Y");
	 * result.setDate(findEvent.getDate()); result.setClnn(findEvent.getClnn());
	 * result.setEventId(findEvent.getEventId()); return result; } else // clnn,
	 * event로 query 조회 결과가 없는경우 {
	 * event.setDate(LocalDateTime.now().format(DateTimeFormatter.
	 * ofPattern("yyyy-MM-dd HH:mm:ss"))); //eventRepository.save(event);
	 * result.setApply("N"); result.setDate(event.getDate());
	 * result.setClnn(event.getClnn()); result.setEventId(event.getEventId());
	 * return result; } }
	 * 
	 * public EventResult eventTest(Event event) { EventResult result = new
	 * EventResult();
	 * 
	 * result.setApply("N");
	 * result.setDate(LocalDateTime.now().format(DateTimeFormatter.
	 * ofPattern("yyyy-MM-dd HH:mm:ss"))); result.setClnn(event.getClnn());
	 * result.setEventId(event.getEventId());
	 * 
	 * Event findEvent =
	 * eventRepository.findByClnnAndEventId(event.getClnn(),event.getEventId());
	 * //System.out.println(findEvent); if(findEvent != null) {
	 * result.setApply("Y"); result.setDate(findEvent.getDate()); return result; }
	 * else { return result; } }
	 */
}
