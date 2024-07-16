package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;

import com.brijframework.content.device.model.UIDeviceMindSetLibarary;
import com.brijframework.content.device.service.DeviceMindSetLibararyService;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;


@RestController
@RequestMapping("/api/device/mindset/libarary")
public class DeviceMindSetLibararyController implements QueryController<UIDeviceMindSetLibarary, EOGlobalMindSetLibarary, Long>{

	@Autowired
	private DeviceMindSetLibararyService deviceMindSetLibararyService;

	@Override
	public DeviceMindSetLibararyService getService() {
		return deviceMindSetLibararyService;
	}
}
