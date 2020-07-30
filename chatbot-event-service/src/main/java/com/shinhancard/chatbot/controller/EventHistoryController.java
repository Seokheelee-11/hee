package com.shinhancard.chatbot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhancard.chatbot.controller.request.EventHistoryRequest;
import com.shinhancard.chatbot.controller.request.EventInfoRequest;
import com.shinhancard.chatbot.controller.response.EventHistoryResponse;
import com.shinhancard.chatbot.controller.response.EventInfoResponse;
import com.shinhancard.chatbot.domain.EventHistory;
import com.shinhancard.chatbot.repository.EventHistoryRepository;
import com.shinhancard.chatbot.service.EventHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("eventHistory")
@RequiredArgsConstructor
public class EventHistoryController {

	private final EventHistoryService eventHistoryService;
	private final EventHistoryRepository eventHistoryRepository;

	@GetMapping
	public List<EventHistory> getEventHistories() {
		return eventHistoryRepository.findAll();
	}
	
	@GetMapping("{id}")
	public EventHistory getEventHistoryById(@PathVariable String id) {
		return eventHistoryService.getEventHistoryById(id);
	}
	
	@PostMapping
	public EventHistoryResponse registEventHistory(@RequestBody EventHistoryRequest eventHistoryRequest) {
		return eventHistoryService.registEventHistory(eventHistoryRequest);
	}
	
	@PutMapping("{id}")
	public EventHistory updateEventHistory(@PathVariable String id, @RequestBody EventHistory event) {
		return eventHistoryService.updateEventHistory(id, event);
	}
	
	@DeleteMapping("{id}")
	public void deleteEventHistory(@PathVariable String id) {
		eventHistoryService.deleteEventHistory(id);
	}
}
