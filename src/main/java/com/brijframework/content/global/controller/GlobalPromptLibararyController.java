package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;
import com.brijframework.content.global.service.GlobalPromptLibararyService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/prompt/libarary")
@Hidden
public class GlobalPromptLibararyController implements CrudController<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> {

	@Autowired
	private GlobalPromptLibararyService globalPromptLibararyService;
	
	@Override
	public CrudService<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> getService() {
		return globalPromptLibararyService;
	}
	
	@GetMapping("/type/{type}")
	public Response findAllByType(@PathVariable String type,   @RequestHeader(required =false)  MultiValueMap<String,String> headers){
		Response response=new Response();
		try {
			response.setData(globalPromptLibararyService.findAllByType(type, headers));
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
