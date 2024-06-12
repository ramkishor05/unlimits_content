package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubTag;
import com.brijframework.content.device.service.DeviceSubTagService;
import com.brijframework.content.global.entities.EOGlobalCategoryTag;


@RestController
@RequestMapping("/api/device/sub/tags")
public class DeviceSubTagController extends QueryController<UIDeviceSubTag, EOGlobalCategoryTag, Long>{

	@Autowired
	private DeviceSubTagService deviceSubTagService;
	
	@GetMapping("/findby/subcategory/{subcategoryId}")
	public Response getSubTagList(@PathVariable("subcategoryId") Long subCategoryId) {
		Response response=new Response();
		try {
			response.setData(deviceSubTagService.findAllBySubCategoryId(subCategoryId));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@Override
	public QueryService<UIDeviceSubTag, EOGlobalCategoryTag, Long> getService() {
		return deviceSubTagService;
	}
}
