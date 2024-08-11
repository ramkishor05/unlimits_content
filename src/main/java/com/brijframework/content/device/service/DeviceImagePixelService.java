package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.device.model.UIDeviceImageModel;

public interface DeviceImagePixelService {
	
	List<UIDeviceImageModel> fetchListFromPexels(Long subCategoryId,  String name);

	List<UIDeviceImageModel> fetchListFromPexels(Long subCategoryId, String type, String name);

	List<UIDeviceImageModel> fetchPageListFromPexels(Long subCategoryId, String type, Integer pageNumber, Integer count);

	List<UIDeviceImageModel> fetchPageListFromPexels(Long subCategoryId, String type, String name, Integer pageNumber, Integer count);

	PageDetail<UIDeviceImageModel> fetchPageObjectFromPexels(Long subCategoryId, String type, Integer pageNumber, Integer count);

	PageDetail<UIDeviceImageModel> fetchPageObjectForPexels(Long subCategoryId, String type, String name, Integer pageNumber, Integer count);
	

}
