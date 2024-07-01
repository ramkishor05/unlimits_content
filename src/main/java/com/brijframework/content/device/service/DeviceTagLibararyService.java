package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceTagLibarary;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;


public interface DeviceTagLibararyService extends QueryService<UIDeviceTagLibarary, EOGlobalTagLibarary, Long>{

	List<UIDeviceTagLibarary> findAllBySubCategoryId(Long subCategoryId);

	/**
	 * @param subCategoryId
	 * @param name
	 * @return
	 */
	List<UIDeviceTagLibarary> search(Long subCategoryId, String name);

}
