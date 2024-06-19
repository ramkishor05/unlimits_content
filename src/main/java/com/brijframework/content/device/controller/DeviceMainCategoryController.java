package com.brijframework.content.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.model.UIDeviceMainCategory;
import com.brijframework.content.device.service.DeviceMainCategoryService;
import com.brijframework.content.global.entities.EOGlobalMainCategory;

@RestController
@RequestMapping("api/device/main/category")
@CrossOrigin("*")
public class DeviceMainCategoryController extends QueryController<UIDeviceMainCategory, EOGlobalMainCategory, Long> {

	@Autowired
	private DeviceMainCategoryService deviceMainCategoryService;
	
	@Override
	public QueryService<UIDeviceMainCategory, EOGlobalMainCategory, Long> getService() {
		return deviceMainCategoryService;
	}
	
	@GetMapping("/status/{status}")
	public Response getCategoryGroupList(@PathVariable("status") RecordStatus  dataStatus) {
		Response response=new Response();
		try {
			response.setData(deviceMainCategoryService.getCategoryGroupList(dataStatus));
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
