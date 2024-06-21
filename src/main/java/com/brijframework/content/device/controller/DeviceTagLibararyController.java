package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.content.device.service.DeviceTagLibararyService;

import static com.brijframework.content.constants.ClientConstants.FAILED;
import static com.brijframework.content.constants.ClientConstants.SUCCESS;
import static com.brijframework.content.constants.ClientConstants.SUCCESSFULLY_PROCCEED;

@RestController
@RequestMapping("/api/device/tag/libarary")
public class DeviceTagLibararyController {
	
	@Autowired
	private DeviceTagLibararyService deviceTagLibararyService;
	
	@GetMapping("/findby/subcategory/{subcategoryId}")
	public Response getTagLibararyList(@PathVariable("subcategoryId") Long subCategoryId) {
		Response response=new Response();
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
