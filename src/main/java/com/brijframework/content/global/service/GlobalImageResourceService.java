package com.brijframework.content.global.service;

import java.io.File;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.constants.ResourceType;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalImageLibarary;


public interface GlobalImageResourceService extends CrudService<UIGlobalImageLibarary, EOGlobalImageLibarary, Long>{

	void importData(ResourceType resourceType);
	
	void exportData(File dirs);

}
