package com.brijframework.content.client.controller;
import static com.brijframework.content.constants.Constants.CUST_APP_ID;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.client.rqrs.CustPromptItemRequest;
import com.brijframework.content.client.rqrs.CustPromptItemResponse;
import com.brijframework.content.client.service.CustPromptItemService;

@RestController
@RequestMapping("/api/client/Prompt/item")
public class CustPromptItemController {

	@Autowired
	private CustPromptItemService custPromptService;
	
	@PostMapping
	public CustPromptItemResponse addPrompt(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustPromptItemRequest custPromptRequest) {
		return custPromptService.savePrompt(custAppId,custPromptRequest);
	}
	
	@PutMapping
	public CustPromptItemResponse updatePrompt(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustPromptItemRequest custPromptRequest) {
		return custPromptService.savePrompt(custAppId,custPromptRequest);
	}
	
	@GetMapping
	public List<CustPromptItemResponse> getPromptList(@RequestHeader(CUST_APP_ID) long custAppId) {
		return custPromptService.getPromptList(custAppId);
	}
	
	@GetMapping("/{id}")
	public CustPromptItemResponse getPromptList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable("id") Long id) {
		return custPromptService.getPrompt(custAppId,id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deletePromptList(@RequestHeader(CUST_APP_ID) long custAppId, @PathVariable("id") Long id) {
		return custPromptService.deletePrompt(custAppId,id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<CustPromptItemResponse> getPromptList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable(TYPE_ID) String typeId) {
		return custPromptService.findAllByType(custAppId,typeId);
	}
}
