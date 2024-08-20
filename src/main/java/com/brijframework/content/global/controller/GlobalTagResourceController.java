package com.brijframework.content.global.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.service.GlobalTagResourceService;


@RestController
@RequestMapping("/api/global/tag/resource")
public class GlobalTagResourceController {
	
	/**
	 * 
	 */
	public static final String SUCCESSFULLY_PROCCEED = "Successfully procceed";
	/**
	 * 
	 */
	public static final String FAILED = "0";
	/**
	 * 
	 */
	public static final String SUCCESS = "1";

	@Autowired
	private GlobalTagResourceService globalTagResourceService;
	
	@PostMapping(value="/import/csv/data", consumes = {MediaType.TEXT_PLAIN_VALUE})
	public Response<List<UIGlobalTagLibarary> > importCsvData(@RequestBody String csvData){
		Response<List<UIGlobalTagLibarary>> response=new Response<List<UIGlobalTagLibarary>>();
		try {
			response.setData(globalTagResourceService.importCsv(csvData));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@PostMapping(value="/import/csv/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Response<List<UIGlobalTagLibarary> > importCsvFile(@RequestBody MultipartFile multipartFile){
		Response<List<UIGlobalTagLibarary>> response=new Response<List<UIGlobalTagLibarary>>();
		try {
			response.setData(globalTagResourceService.importCsv(new String(multipartFile.getBytes())));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
}
