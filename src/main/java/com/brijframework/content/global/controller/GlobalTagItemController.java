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

import com.brijframework.content.global.rqrs.GlobalTagItemRequest;
import com.brijframework.content.global.rqrs.GlobalTagItemResponse;
import com.brijframework.content.global.service.GlobalTagItemService;


@RestController
@RequestMapping("/api/global/tag/item")
public class GlobalTagItemController {

	@Autowired
	private GlobalTagItemService GlobalTagItemService;
	
	@PostMapping
	public GlobalTagItemResponse addTagItem(@RequestBody GlobalTagItemRequest GlobalTagItemRequest) {
		return GlobalTagItemService.saveTagItem(GlobalTagItemRequest);
	}
	
	@PutMapping
	public GlobalTagItemResponse updateTag(@RequestBody GlobalTagItemRequest GlobalTagItemRequest) {
		return GlobalTagItemService.saveTagItem(GlobalTagItemRequest);
	}
	
	@GetMapping
	public List<GlobalTagItemResponse> getTagItemList() {
		return GlobalTagItemService.getTagItemList();
	}
	
	@GetMapping("/{id}")
	public GlobalTagItemResponse getTagItemList(@PathVariable("id") Long id) {
		return GlobalTagItemService.getTagItem(id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteTagList(@PathVariable("id") Long id) {
		return GlobalTagItemService.deleteTagItem(id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<GlobalTagItemResponse> getTagList(@PathVariable(TYPE_ID) String typeId) {
		return GlobalTagItemService.findAllByType(typeId);
	}
}
