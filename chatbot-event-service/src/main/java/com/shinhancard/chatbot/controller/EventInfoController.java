package com.shinhancard.chatbot.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.domain.EventInfo;
import com.shinhancard.chatbot.dto.EventInfoInput;
import com.shinhancard.chatbot.dto.EventInfoOutput;
import com.shinhancard.chatbot.repository.EventInfoRepository;
import com.shinhancard.chatbot.service.EventInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("eventInfo")
@RequiredArgsConstructor
public class EventInfoController {
	
	private final ModelMapper modelMapper;
	private final EventInfoService eventInfoService;
	private final EventInfoRepository eventInfoRepository;

	@GetMapping
	public List<EventInfo> getEvents() {
		return eventInfoRepository.findAll();
	}
	
	@GetMapping("{id}")
	public EventInfo getEventById(@PathVariable String id) {
		return eventInfoService.getEventById(id);
	}
	
	@PostMapping
	public EventInfoOutput registEvent(@RequestBody EventInfoInput eventInfoInput) {
		return eventInfoService.registEvent(eventInfoInput);
	}
	
	@PutMapping("{id}")
	public EventInfo updateEvent(@PathVariable String id, @RequestBody EventInfo event) {
		return eventInfoService.updateEvent(id, event);
	}
	
	@DeleteMapping("{id}")
	public void deleteEvent(@PathVariable String id) {
		eventInfoService.deleteEvent(id);
	}
}
