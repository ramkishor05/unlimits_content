package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceTenure;
import com.brijframework.content.device.service.DeviceTenureService;
import com.brijframework.content.global.entities.EOGlobalTenure;


@RestController
@RequestMapping("/api/device/tenure")
public class DeviceTenureController extends QueryController<UIDeviceTenure, EOGlobalTenure, Long>{

	@Autowired
	private DeviceTenureService deviceTenureService;
	
	@Override
	public QueryService<UIDeviceTenure, EOGlobalTenure, Long> getService() {
		return deviceTenureService;
	}
}
