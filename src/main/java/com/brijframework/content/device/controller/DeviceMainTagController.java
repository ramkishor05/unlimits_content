package com.brijframework.content.device.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceMainTag;
import com.brijframework.content.device.service.DeviceMainTagService;
import com.brijframework.content.global.entities.EOGlobalTagGroup;


@RestController
@RequestMapping("/api/device/main/tags")
public class DeviceMainTagController extends QueryController<UIDeviceMainTag, EOGlobalTagGroup, Long> {

	@Autowired
	private DeviceMainTagService deviceMainTagService;
	
	@Override
	public QueryService<UIDeviceMainTag, EOGlobalTagGroup, Long> getService() {
		return deviceMainTagService;
	}
	
	@GetMapping("/findby/subcategory/{subcategoryId}")
	public List<UIDeviceMainTag> getMainTagListBySubCategoryId(@PathVariable("subcategoryId") Long subcategoryId) {
		return deviceMainTagService.findAllBySubCategoryId(subcategoryId);
	}
}
