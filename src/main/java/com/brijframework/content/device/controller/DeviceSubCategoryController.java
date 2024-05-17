package com.brijframework.content.device.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.device.service.DeviceSubCategoryService;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;


@RestController
@RequestMapping("/api/device/subcategory")
@CrossOrigin("*")
public class DeviceSubCategoryController extends QueryController<UIDeviceSubCategory, EOGlobalCategoryItem, Long>{

	@Autowired
	private DeviceSubCategoryService deviceSubCategoryService;
	
	@Override
	public QueryService<UIDeviceSubCategory, EOGlobalCategoryItem, Long> getService() {
		return deviceSubCategoryService;
	}
	
	@GetMapping("/findby/category/{categoryId}")
	public List<UIDeviceSubCategory> getCategoryListByCategoryId(@PathVariable("categoryId") Long categoryId) {
		return deviceSubCategoryService.findAllByCategoryId(categoryId);
	}
	
}
