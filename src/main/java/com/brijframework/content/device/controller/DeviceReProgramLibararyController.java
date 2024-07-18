package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;

import com.brijframework.content.device.model.UIDeviceReProgramLibarary;
import com.brijframework.content.device.service.DeviceReProgramLibararyService;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;


@RestController
@RequestMapping("/api/device/reprogram/libarary")
public class DeviceReProgramLibararyController implements QueryController<UIDeviceReProgramLibarary, EOGlobalReProgramLibarary, Long>{

	@Autowired
	private DeviceReProgramLibararyService deviceReProgramLibararyService;

	@Override
	public DeviceReProgramLibararyService getService() {
		return deviceReProgramLibararyService;
	}
}
