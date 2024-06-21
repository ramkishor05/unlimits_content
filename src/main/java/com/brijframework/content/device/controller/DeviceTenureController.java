package com.brijframework.content.device.controller;
import static com.brijframework.content.constants.ClientConstants.FAILED;
import static com.brijframework.content.constants.ClientConstants.SUCCESS;
import static com.brijframework.content.constants.ClientConstants.SUCCESSFULLY_PROCCEED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.content.device.service.DeviceTenureService;

@RestController
@RequestMapping("/api/device/tenure")
public class DeviceTenureController {

	@Autowired
	private DeviceTenureService deviceTenureService;

	@GetMapping
	public Response findAll(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
		Response response=new Response();
		try {
			response.setData(deviceTenureService.findAll(headers));
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
