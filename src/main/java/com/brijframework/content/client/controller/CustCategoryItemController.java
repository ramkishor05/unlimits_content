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

import com.brijframework.content.client.rqrs.CustCategoryItemRequest;
import com.brijframework.content.client.rqrs.CustCategoryItemResponse;
import com.brijframework.content.client.service.CustCategoryItemService;

@RestController
@RequestMapping("/api/client/category/item")
public class CustCategoryItemController {

	@Autowired
	private CustCategoryItemService custCategoryService;
	
	@PostMapping
	public CustCategoryItemResponse addCategory(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustCategoryItemRequest custCategoryRequest) {
		return custCategoryService.saveCategory(custAppId,custCategoryRequest);
	}
	
	@PutMapping
	public CustCategoryItemResponse updateCategory(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustCategoryItemRequest custCategoryRequest) {
		return custCategoryService.saveCategory(custAppId,custCategoryRequest);
	}
	
	@GetMapping
	public List<CustCategoryItemResponse> getCategoryList(@RequestHeader(CUST_APP_ID) long custAppId) {
		return custCategoryService.getCategoryList(custAppId);
	}
	
	@GetMapping("/{id}")
	public CustCategoryItemResponse getCategoryList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable("id") Long id) {
		return custCategoryService.getCategory(custAppId,id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteCategoryList(@RequestHeader(CUST_APP_ID) long custAppId, @PathVariable("id") Long id) {
		return custCategoryService.deleteCategory(custAppId,id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<CustCategoryItemResponse> getCategoryList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable(TYPE_ID) String typeId) {
		return custCategoryService.findAllByType(custAppId,typeId);
	}
}
