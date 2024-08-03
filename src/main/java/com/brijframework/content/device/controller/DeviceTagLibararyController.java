package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceTagLibarary;
import com.brijframework.content.device.service.DeviceTagLibararyService;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;

@RestController
@RequestMapping("/api/device/tag/libarary")
public class DeviceTagLibararyController implements QueryController<UIDeviceTagLibarary, EOGlobalTagLibarary, Long>{
	
	@Autowired
	private DeviceTagLibararyService deviceTagLibararyService;

	@Override
	public QueryService<UIDeviceTagLibarary, EOGlobalTagLibarary, Long> getService() {
		return deviceTagLibararyService;
	}
	
	@GetMapping("/findby/subcategory/{subcategoryId}")
	public Response<Object> getTagLibararyList(@PathVariable("subcategoryId") Long subCategoryId) {
		Response<Object> response=new Response<Object>();
		try {
			response.setData(deviceTagLibararyService.findAllBySubCategoryId(subCategoryId));
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
