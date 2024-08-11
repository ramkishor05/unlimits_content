package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceImageModel;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;


public interface DeviceImageLibararyService extends QueryService<UIDeviceImageModel, EOGlobalImageLibarary, Long>{
	
	List<String> getTypes(Long subCategoryId);

}
