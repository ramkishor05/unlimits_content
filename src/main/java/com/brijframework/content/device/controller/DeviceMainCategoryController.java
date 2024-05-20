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

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.model.UIDeviceMainCategory;
import com.brijframework.content.device.service.DeviceMainCategoryService;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;

@RestController
@RequestMapping("api/device/main/category")
@CrossOrigin("*")
public class DeviceMainCategoryController extends QueryController<UIDeviceMainCategory, EOGlobalCategoryGroup, Long> {

	@Autowired
	private DeviceMainCategoryService deviceMainCategoryService;
	
	@Override
	public QueryService<UIDeviceMainCategory, EOGlobalCategoryGroup, Long> getService() {
		return deviceMainCategoryService;
	}
	
	@GetMapping("/status/{status}")
	public List<UIDeviceMainCategory> getCategoryGroupList(@PathVariable("status") RecordStatus  dataStatus) {
		return deviceMainCategoryService.getCategoryGroupList(dataStatus);
	}
	
}