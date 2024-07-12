package com.brijframework.content.device.service;

import java.util.List;

import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;


public interface DeviceImageLibararyService extends QueryService<UIDeviceImageLibarary, EOGlobalImageLibarary, Long>{

	List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId, String name);
	
	List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId);

	PageDetail search(MultiValueMap<String,String> headers,Long subCategoryId, Long tagLibararyId, int pageNumber, int count);

	PageDetail search(MultiValueMap<String,String> headers,Long subCategoryId, Long tagLibararyId, String name, int pageNumber, int count);

}
