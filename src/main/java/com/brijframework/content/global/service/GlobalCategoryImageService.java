package com.brijframework.content.global.service;

import java.io.IOException;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalCategoryImage;
import com.brijframework.content.global.model.UIGlobalCategoryImage;


public interface GlobalCategoryImageService extends CrudService<UIGlobalCategoryImage, EOGlobalCategoryImage, Long>{

	/**
	 * @throws IOException
	 */
	void init() throws IOException;

}
