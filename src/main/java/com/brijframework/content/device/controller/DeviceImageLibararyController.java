package com.brijframework.content.device.controller;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CQRSController;
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

	@GetMapping("/types")
	public Response<List<String>> getTypes(@RequestParam("subCategoryId")Long subCategoryId,  @RequestHeader(required =false) MultiValueMap<String,String> headers, WebRequest webRequest){
		Response<List<String>> response=new Response<List<String>>();
		try {
			response.setData(deviceImageLibararyService.getTypes(subCategoryId));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/groupby/folder")
	public Response<Map<String, List<UIDeviceImageLibarary>>> getImagesGroupbyFolder(@RequestHeader(required =false) MultiValueMap<String,String> headers, WebRequest webRequest){
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
	
		Response<Map<String, List<UIDeviceImageLibarary>>> response=new Response<Map<String, List<UIDeviceImageLibarary>>>();
		try {
			response.setData(deviceImageLibararyService.findAll(headers, filters).stream().collect(Collectors.groupingBy(UIDeviceImageLibarary::getType)));
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
