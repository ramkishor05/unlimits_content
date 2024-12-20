package com.brijframework.content.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.global.entities.EOGlobalSubCategory;

public interface DeviceSubCategoryService extends QueryService<UIDeviceSubCategory, EOGlobalSubCategory, Long>{

	List<UIDeviceSubCategory> findAllByMainCategoryId(Long categoryId, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions);

}
