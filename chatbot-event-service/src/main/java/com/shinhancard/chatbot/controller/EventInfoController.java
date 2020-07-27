package com.shinhancard.chatbot.controller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.controller.result.EventResult;
import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.repository.EventInfoRepository;
import com.shinhancard.chatbot.service.EventInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("eventInfo")
@RequiredArgsConstructor
public class EventInfoController {
	
	private final ModelMapper modelMapper;
	private final EventInfoService eventService;
	private final EventInfoRepository eventRepository;

	@GetMapping
	public List<EventInfo> getEvents() {
		return eventRepository.findAll();
	}
	
	@GetMapping("{id}")
	public EventInfo getEventById(@PathVariable String id) {
		return eventService.getEventById(id);
	}
	
	@PostMapping
	public EventInfo registEvent(@RequestBody EventInfo event) {
		return eventService.registEvent(event);
	}
	
	@PutMapping("{id}")
	public EventInfo updateEvent(@PathVariable String id, @RequestBody EventInfo event) {
		return eventService.updateEvent(id, event);
	}
	
	@DeleteMapping("{id}")
	public void deleteEvent(@PathVariable String id) {
		eventService.deleteEvent(id);
	}
}
