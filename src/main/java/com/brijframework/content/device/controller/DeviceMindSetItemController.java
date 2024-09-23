package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;

import com.brijframework.content.device.model.UIDeviceMindSetItemModel;
import com.brijframework.content.device.service.DeviceMindSetItemService;
import com.brijframework.content.global.entities.EOGlobalMindSetItem;


@RestController
@RequestMapping("/api/device/mindset/item")
public class DeviceMindSetItemController implements QueryController<UIDeviceMindSetItemModel, EOGlobalMindSetItem, Long>{

	@Autowired
	private DeviceMindSetItemService deviceMindSetItemService;

	@Override
	public DeviceMindSetItemService getService() {
		return deviceMindSetItemService;
	}
}
