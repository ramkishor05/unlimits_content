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

import com.brijframework.content.client.rqrs.CustMediaItemRequest;
import com.brijframework.content.client.rqrs.CustMediaItemResponse;
import com.brijframework.content.client.service.CustMediaItemService;

@RestController
@RequestMapping("/api/client/Media/item")
public class CustMediaItemController {

	@Autowired
	private CustMediaItemService custMediaService;
	
	@PostMapping
	public CustMediaItemResponse addMedia(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustMediaItemRequest custMediaRequest) {
		return custMediaService.saveMedia(custAppId,custMediaRequest);
	}
	
	@PutMapping
	public CustMediaItemResponse updateMedia(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustMediaItemRequest custMediaRequest) {
		return custMediaService.saveMedia(custAppId,custMediaRequest);
	}
	
	@GetMapping
	public List<CustMediaItemResponse> getMediaList(@RequestHeader(CUST_APP_ID) long custAppId) {
		return custMediaService.getMediaList(custAppId);
	}
	
	@GetMapping("/{id}")
	public CustMediaItemResponse getMediaList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable("id") Long id) {
		return custMediaService.getMedia(custAppId,id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteMediaList(@RequestHeader(CUST_APP_ID) long custAppId, @PathVariable("id") Long id) {
		return custMediaService.deleteMedia(custAppId,id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<CustMediaItemResponse> getMediaList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable(TYPE_ID) String typeId) {
		return custMediaService.findAllByType(custAppId,typeId);
	}
}
