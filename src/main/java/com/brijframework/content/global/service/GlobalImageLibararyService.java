package com.brijframework.content.global.service;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.model.UIGlobalImageLibarary;


public interface GlobalImageLibararyService extends CrudService<UIGlobalImageLibarary, EOGlobalImageLibarary, Long>{

	void saveImageTagMappings(EOGlobalSubCategory subCategory, EOGlobalImageLibarary globalImageLibarary,
			String tagNames);

}
