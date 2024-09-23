package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;

import com.brijframework.content.device.model.UIDeviceReProgramItem;
import com.brijframework.content.device.service.DeviceReProgramItemService;
import com.brijframework.content.global.entities.EOGlobalReProgramItem;


@RestController
@RequestMapping("/api/device/reprogram/item")
public class DeviceReProgramItemController implements QueryController<UIDeviceReProgramItem, EOGlobalReProgramItem, Long>{

	@Autowired
	private DeviceReProgramItemService deviceReProgramItemService;

	@Override
	public DeviceReProgramItemService getService() {
		return deviceReProgramItemService;
	}
}
