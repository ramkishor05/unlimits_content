package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;

import com.brijframework.content.device.model.UIDeviceAffirmationLibarary;
import com.brijframework.content.device.service.DeviceAffirmationLibararyService;
import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;


@RestController
@RequestMapping("/api/device/affirmation/libarary")
public class DeviceAffirmationLibararyController implements QueryController<UIDeviceAffirmationLibarary, EOGlobalAffirmationLibarary, Long>{

	@Autowired
	private DeviceAffirmationLibararyService deviceAffirmationLibararyService;

	@Override
	public DeviceAffirmationLibararyService getService() {
		return deviceAffirmationLibararyService;
	}
}
