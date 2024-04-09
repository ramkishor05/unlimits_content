package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;


public interface GlobalCategoryItemService {

	UIGlobalCategoryItem saveCategory(UIGlobalCategoryItem globalCategoryItem);

	GlobalCategoryItemResponse getCategory(Long id);

	List<UIGlobalCategoryItem> getCategoryList();

	List<GlobalCategoryItemResponse> findAllByType(String typeId);

	boolean deleteCategory(Long id);

}
