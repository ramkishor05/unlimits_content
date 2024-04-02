package com.brijframework.content.global.controller;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.global.rqrs.GlobalCategoryItemRequest;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;
import com.brijframework.content.global.service.GlobalCategoryItemService;


@RestController
@RequestMapping("/api/global/category/item")
@CrossOrigin("*")
public class GlobalCategoryItemController {

	@Autowired
	private GlobalCategoryItemService GlobalCategoryItemService;
	
	@PostMapping
	public GlobalCategoryItemResponse addCategory(@RequestBody GlobalCategoryItemRequest GlobalCategoryItemRequest) {
		return GlobalCategoryItemService.saveCategory(GlobalCategoryItemRequest);
	}
	
	@PutMapping
	public GlobalCategoryItemResponse updateCategory(@RequestBody GlobalCategoryItemRequest GlobalCategoryItemRequest) {
		return GlobalCategoryItemService.saveCategory(GlobalCategoryItemRequest);
	}
	
	@GetMapping
	public List<GlobalCategoryItemResponse> getCategoryList() {
		return GlobalCategoryItemService.getCategoryList();
	}
	
	@GetMapping("/{id}")
	public GlobalCategoryItemResponse getCategoryList(@PathVariable("id") Long id) {
		return GlobalCategoryItemService.getCategory(id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteCategoryList(@PathVariable("id") Long id) {
		return GlobalCategoryItemService.deleteCategory(id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<GlobalCategoryItemResponse> getCategoryList(@PathVariable(TYPE_ID) String typeId) {
		return GlobalCategoryItemService.findAllByType(typeId);
	}
}
