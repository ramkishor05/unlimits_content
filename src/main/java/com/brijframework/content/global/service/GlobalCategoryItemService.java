package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;
import com.brijframework.content.service.CrudService;


public interface GlobalCategoryItemService extends CrudService<UIGlobalCategoryItem, EOGlobalCategoryItem, Long>{

	List<GlobalCategoryItemResponse> findAllByType(String typeId);

}
