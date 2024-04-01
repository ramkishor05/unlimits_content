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

import com.brijframework.content.client.rqrs.CustTagItemRequest;
import com.brijframework.content.client.rqrs.CustTagItemResponse;
import com.brijframework.content.client.service.CustTagItemService;

@RestController
@RequestMapping("/api/client/Tag/item")
public class CustTagItemController {

	@Autowired
	private CustTagItemService custTagService;
	
	@PostMapping
	public CustTagItemResponse addTag(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustTagItemRequest custTagRequest) {
		return custTagService.saveTag(custAppId,custTagRequest);
	}
	
	@PutMapping
	public CustTagItemResponse updateTag(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustTagItemRequest custTagRequest) {
		return custTagService.saveTag(custAppId,custTagRequest);
	}
	
	@GetMapping
	public List<CustTagItemResponse> getTagList(@RequestHeader(CUST_APP_ID) long custAppId) {
		return custTagService.getTagList(custAppId);
	}
	
	@GetMapping("/{id}")
	public CustTagItemResponse getTagList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable("id") Long id) {
		return custTagService.getTag(custAppId,id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteTagList(@RequestHeader(CUST_APP_ID) long custAppId, @PathVariable("id") Long id) {
		return custTagService.deleteTag(custAppId,id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<CustTagItemResponse> getTagList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable(TYPE_ID) String typeId) {
		return custTagService.findAllByType(custAppId,typeId);
	}
}
