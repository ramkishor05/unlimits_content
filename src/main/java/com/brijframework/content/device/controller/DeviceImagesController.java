package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceMediaItem;
import com.brijframework.content.device.service.DeviceMediaItemService;
import com.brijframework.content.global.entities.EOGlobalMediaItem;


@RestController
@RequestMapping("/api/device/images")
public class DeviceImagesController extends QueryController<UIDeviceMediaItem, EOGlobalMediaItem, Long>{

	@Autowired
	private DeviceMediaItemService deviceMediaItemService;
	
	@Override
	public QueryService<UIDeviceMediaItem, EOGlobalMediaItem, Long> getService() {
		return deviceMediaItemService;
	}
	
	@GetMapping("/search/{name}")
	public Response search(@PathVariable String name){
		Response response=new Response();
		try {
			response.setData(deviceMediaItemService.search(name));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
}
