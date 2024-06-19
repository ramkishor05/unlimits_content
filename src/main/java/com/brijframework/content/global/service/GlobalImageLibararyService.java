package com.brijframework.content.global.service;

import java.io.IOException;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalImageLibarary;


public interface GlobalImageLibararyService extends CrudService<UIGlobalImageLibarary, EOGlobalImageLibarary, Long>{

	/**
	 * @throws IOException
	 */
	void init() throws IOException;

}
