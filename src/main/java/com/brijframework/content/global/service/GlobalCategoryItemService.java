package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;


public interface GlobalCategoryItemService extends CrudService<UIGlobalCategoryItem, EOGlobalCategoryItem, Long>{

	List<GlobalCategoryItemResponse> findAllByType(String typeId);

}
