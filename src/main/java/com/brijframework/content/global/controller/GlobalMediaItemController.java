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

import com.brijframework.content.global.rqrs.GlobalMediaItemRequest;
import com.brijframework.content.global.rqrs.GlobalMediaItemResponse;
import com.brijframework.content.global.service.GlobalMediaItemService;


@RestController
@RequestMapping("/api/global/Media/item")
public class GlobalMediaItemController {

	@Autowired
	private GlobalMediaItemService GlobalMediaItemService;
	
	@PostMapping
	public GlobalMediaItemResponse addMedia(@RequestBody GlobalMediaItemRequest GlobalMediaItemRequest) {
		return GlobalMediaItemService.saveMedia(GlobalMediaItemRequest);
	}
	
	@PutMapping
	public GlobalMediaItemResponse updateMedia(@RequestBody GlobalMediaItemRequest GlobalMediaItemRequest) {
		return GlobalMediaItemService.saveMedia(GlobalMediaItemRequest);
	}
	
	@GetMapping
	public List<GlobalMediaItemResponse> getMediaList() {
		return GlobalMediaItemService.getMediaList();
	}
	
	@GetMapping("/{id}")
	public GlobalMediaItemResponse getMediaList(@PathVariable("id") Long id) {
		return GlobalMediaItemService.getMedia(id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteMediaList(@PathVariable("id") Long id) {
		return GlobalMediaItemService.deleteMedia(id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<GlobalMediaItemResponse> getMediaList(@PathVariable(TYPE_ID) String typeId) {
		return GlobalMediaItemService.findAllByType(typeId);
	}
}
