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

import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.device.service.DeviceSubCategoryService;
import com.brijframework.content.global.entities.EOGlobalSubCategory;


@RestController
@RequestMapping("/api/device/sub/category")
@CrossOrigin("*")
public class DeviceSubCategoryController extends QueryController<UIDeviceSubCategory, EOGlobalSubCategory, Long>{

	@Autowired
	private DeviceSubCategoryService deviceSubCategoryService;
	
	@Override
	public QueryService<UIDeviceSubCategory, EOGlobalSubCategory, Long> getService() {
		return deviceSubCategoryService;
	}
	
	@GetMapping("/findby/category/{categoryId}")
	public Response getCategoryListByCategoryId(@PathVariable("categoryId") Long categoryId) {
		Response response=new Response();
		try {
			response.setData(deviceSubCategoryService.findAllByCategoryId(categoryId));
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
