package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceExampleLibarary;
import com.brijframework.content.device.service.DeviceExampleLibararyService;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;

@RestController
@RequestMapping("/api/device/example/libarary")
public class DeviceExampleLibararyController implements QueryController<UIDeviceExampleLibarary, EOGlobalExampleLibarary, Long>{
	
	@Autowired
	private DeviceExampleLibararyService deviceExampleLibararyService;

	@Override
	public QueryService<UIDeviceExampleLibarary, EOGlobalExampleLibarary, Long> getService() {
		return deviceExampleLibararyService;
	}

}
