package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.model.UIGlobalSubCategory;


public interface GlobalSubCategoryService extends CrudService<UIGlobalSubCategory, EOGlobalSubCategory, Long>{

	void init(List<EOGlobalSubCategory> eoGlobalCategoryItemJson);

	void export();

}
