package com.brijframework.content.global.service;

import java.util.List;

import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;


public interface GlobalPromptLibararyService extends CrudService<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long>{

	/**
	 * @param type
	 * @param headers
	 * @return
	 */
	List<UIGlobalPromptLibarary> findAllByType(String type, MultiValueMap<String, String> headers);

	void init(List<EOGlobalPromptLibarary> eoGlobalPromptJson);

}
