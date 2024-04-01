package com.brijframework.content.global.controller;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.global.rqrs.GlobalPromptItemRequest;
import com.brijframework.content.global.rqrs.GlobalPromptItemResponse;
import com.brijframework.content.global.service.GlobalPromptItemService;


@RestController
@RequestMapping("/api/global/Prompt/item")
public class GlobalPromptItemController {

	@Autowired
	private GlobalPromptItemService GlobalPromptItemService;
	
	@PostMapping
	public GlobalPromptItemResponse addPrompt(@RequestBody GlobalPromptItemRequest GlobalPromptItemRequest) {
		return GlobalPromptItemService.savePrompt(GlobalPromptItemRequest);
	}
	
	@PutMapping
	public GlobalPromptItemResponse updatePrompt(@RequestBody GlobalPromptItemRequest GlobalPromptItemRequest) {
		return GlobalPromptItemService.savePrompt(GlobalPromptItemRequest);
	}
	
	@GetMapping
	public List<GlobalPromptItemResponse> getPromptList() {
		return GlobalPromptItemService.getPromptList();
	}
	
	@GetMapping("/{id}")
	public GlobalPromptItemResponse getPromptList(@PathVariable("id") Long id) {
		return GlobalPromptItemService.getPrompt(id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deletePromptList(@PathVariable("id") Long id) {
		return GlobalPromptItemService.deletePrompt(id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<GlobalPromptItemResponse> getPromptList(@PathVariable(TYPE_ID) String typeId) {
		return GlobalPromptItemService.findAllByType(typeId);
	}
}
