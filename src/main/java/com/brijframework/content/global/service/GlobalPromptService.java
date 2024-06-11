package com.brijframework.content.global.service;

import java.util.List;

import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPrompt;
import com.brijframework.content.global.model.UIGlobalPrompt;


public interface GlobalPromptService extends CrudService<UIGlobalPrompt, EOGlobalPrompt, Long>{

	/**
	 * @param type
	 * @param headers
	 * @return
	 */
	List<UIGlobalPrompt> findAllByType(String type, MultiValueMap<String, String> headers);

}
