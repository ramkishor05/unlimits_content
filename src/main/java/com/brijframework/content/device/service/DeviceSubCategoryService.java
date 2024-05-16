package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;

public interface DeviceSubCategoryService extends QueryService<UIDeviceSubCategory, EOGlobalCategoryItem, Long>{

	List<UIDeviceSubCategory> findAllByCategoryId(Long categoryId);

}
