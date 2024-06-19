package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.service.DeviceImageLibararyService;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;


@RestController
@RequestMapping("/api/device/image/libarary")
public class DeviceImageLibararyController extends QueryController<UIDeviceImageLibarary, EOGlobalImageLibarary, Long>{

	@Autowired
	private DeviceImageLibararyService deviceImageLibararyService;
	
	@Override
	public QueryService<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> getService() {
		return deviceImageLibararyService;
	}
	
	@GetMapping("/subcategory/{subCategoryId}/search/{name}")
	public Response search(@PathVariable Long subCategoryId, @PathVariable String name){
		Response response=new Response();
		try {
			response.setData(deviceImageLibararyService.search(subCategoryId, name));
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
