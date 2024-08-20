package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.device.model.UIDeviceImageLibarary;

public interface DeviceImagePixelService {
	
	List<UIDeviceImageLibarary> fetchListFromPexels(Long subCategoryId,  String name);

	List<UIDeviceImageLibarary> fetchListFromPexels(Long subCategoryId, String type, String name);

	List<UIDeviceImageLibarary> fetchPageListFromPexels(Long subCategoryId, String type, Integer pageNumber, Integer count);

	List<UIDeviceImageLibarary> fetchPageListFromPexels(Long subCategoryId, String type, String name, Integer pageNumber, Integer count);

	PageDetail<UIDeviceImageLibarary> fetchPageObjectFromPexels(Long subCategoryId, String type, Integer pageNumber, Integer count);

	PageDetail<UIDeviceImageLibarary> fetchPageObjectForPexels(Long subCategoryId, String type, String name, Integer pageNumber, Integer count);
	

}
