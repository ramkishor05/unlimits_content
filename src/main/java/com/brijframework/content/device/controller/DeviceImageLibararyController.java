package com.brijframework.content.device.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.service.DeviceImageLibararyService;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;

@RestController
@RequestMapping("/api/device/image/libarary")
public class DeviceImageLibararyController implements QueryController<UIDeviceImageLibarary, EOGlobalImageLibarary, Long>{

	@Autowired
	private DeviceImageLibararyService deviceImageLibararyService;
	
	@Override
	public QueryService<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> getService() {
		return deviceImageLibararyService;
	}
	
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
	
	@GetMapping("/subcategory/{subCategoryId}/taglibarary/{tagLibararyId}/search")
	public Response search(@PathVariable Long subCategoryId,@PathVariable Long tagLibararyId, @RequestParam(required = false) String name){
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
	
	@GetMapping("/subcategory/{subCategoryId}/taglibarary/{tagLibararyId}/page/data/{pageNumber}/count/{count}")
	public Response search(@RequestHeader(required =false) MultiValueMap<String,String> headers,@PathVariable Long subCategoryId,@PathVariable Long tagLibararyId,@PathVariable int pageNumber,
			@PathVariable int count){
		Response response=new Response();
		try {
			response.setData(deviceImageLibararyService.search(headers,subCategoryId, tagLibararyId, pageNumber, count));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/subcategory/{subCategoryId}/taglibarary/{tagLibararyId}/page/data/{pageNumber}/count/{count}/search")
	public Response search(@RequestHeader(required =false) MultiValueMap<String,String> headers, @PathVariable Long subCategoryId,@PathVariable Long tagLibararyId, @RequestParam(required = false) String name,@PathVariable int pageNumber,
			@PathVariable int count){
		Response response=new Response();
		try {
			response.setData(deviceImageLibararyService.search(headers, subCategoryId, tagLibararyId, name, pageNumber, count));
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
