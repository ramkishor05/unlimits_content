package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.rqrs.GlobalCategoryItemRequest;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;


public interface GlobalCategoryItemService {

	GlobalCategoryItemResponse saveCategory(GlobalCategoryItemRequest GlobalCategoryItemRequest);

	GlobalCategoryItemResponse getCategory(Long id);

	List<GlobalCategoryItemResponse> getCategoryList();

	List<GlobalCategoryItemResponse> findAllByType(String typeId);

	boolean deleteCategory(Long id);

}
