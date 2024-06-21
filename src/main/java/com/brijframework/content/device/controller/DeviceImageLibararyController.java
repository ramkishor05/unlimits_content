package com.brijframework.content.device.controller;
import static com.brijframework.content.constants.ClientConstants.FAILED;
import static com.brijframework.content.constants.ClientConstants.SUCCESS;
import static com.brijframework.content.constants.ClientConstants.SUCCESSFULLY_PROCCEED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.content.device.service.DeviceImageLibararyService;

@RestController
@RequestMapping("/api/device/image/libarary")
public class DeviceImageLibararyController {

	@Autowired
	private DeviceImageLibararyService deviceImageLibararyService;
	
	@GetMapping("/subcategory/{subCategoryId}/taglibarary/{tagLibararyId}")
	public Response search(@PathVariable Long subCategoryId,@PathVariable Long tagLibararyId){
		Response response=new Response();
		try {
			response.setData(deviceImageLibararyService.search(subCategoryId, tagLibararyId));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/subcategory/{subCategoryId}/taglibarary/{tagLibararyId}/search/{name}")
	public Response search(@PathVariable Long subCategoryId,@PathVariable Long tagLibararyId, @PathVariable String name){
		Response response=new Response();
		try {
			response.setData(deviceImageLibararyService.search(subCategoryId, tagLibararyId, name));
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
