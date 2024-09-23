package com.brijframework.content.device.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CQRSController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubCategory;
import com.brijframework.content.device.service.DeviceSubCategoryService;
import com.brijframework.content.global.entities.EOGlobalSubCategory;

@RestController
@RequestMapping("/api/device/sub/category")
@CrossOrigin("*")
public class DeviceSubCategoryController implements QueryController<UIDeviceSubCategory, EOGlobalSubCategory, Long>{
	
	@Autowired
	private DeviceSubCategoryService deviceSubCategoryService;
	
	@Override
	public QueryService<UIDeviceSubCategory, EOGlobalSubCategory, Long> getService() {
		return deviceSubCategoryService;
	}
	
	@GetMapping("/findby/maincategory/{mainCategoryId}")
	public Response<Object> getCategoryListByCategoryId(@PathVariable("mainCategoryId") Long mainCategoryId, @RequestHeader(required =false)  MultiValueMap<String,String> headers, WebRequest webRequest) {
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> actions = CQRSController.getActions(webRequest);
		Response<Object> response=new Response<Object>();
		try {
			response.setData(deviceSubCategoryService.findAllByMainCategoryId(mainCategoryId, headers, filters, actions));
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
