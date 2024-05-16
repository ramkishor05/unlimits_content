package com.brijframework.content.device.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubTag;
import com.brijframework.content.device.service.DeviceSubTagService;
import com.brijframework.content.global.entities.EOGlobalTagItem;


@RestController
@RequestMapping("/api/device/subtag")
public class DeviceSubTagController extends QueryController<UIDeviceSubTag, EOGlobalTagItem, Long>{

	@Autowired
	private DeviceSubTagService deviceSubTagService;
	
	@GetMapping("/tag/{tagId}")
	public List<UIDeviceSubTag> getTagList(@PathVariable("tagId") Long tagId) {
		return deviceSubTagService.findAllByTagId(tagId);
	}

	@Override
	public QueryService<UIDeviceSubTag, EOGlobalTagItem, Long> getService() {
		return deviceSubTagService;
	}
}
