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

import com.brijframework.content.global.entities.EOGlobalPrompt;
import com.brijframework.content.global.model.UIGlobalPrompt;
import com.brijframework.content.global.service.GlobalPromptService;


@RestController
@RequestMapping("/api/global/prompt")
public class GlobalPromptController extends CrudController<UIGlobalPrompt, EOGlobalPrompt, Long> {

	@Autowired
	private GlobalPromptService globalPromptItemService;
	
	@Override
	public CrudService<UIGlobalPrompt, EOGlobalPrompt, Long> getService() {
		return globalPromptItemService;
	}
	
	@GetMapping("/type/{type}")
	public Response findAllByType(@PathVariable String type,   @RequestHeader(required =false)  MultiValueMap<String,String> headers){
		Response response=new Response();
		try {
			response.setData(globalPromptItemService.findAllByType(type, headers));
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
