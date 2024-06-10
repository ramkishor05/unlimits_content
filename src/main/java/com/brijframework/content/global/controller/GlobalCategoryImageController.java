package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalCategoryImage;
import com.brijframework.content.global.model.UIGlobalCategoryImage;
import com.brijframework.content.global.service.GlobalCategoryImageService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/category/image")
@Hidden
public class GlobalCategoryImageController extends CrudController<UIGlobalCategoryImage, EOGlobalCategoryImage, Long>{

	@Autowired
	private GlobalCategoryImageService globalCategoryImageService;
	
	@Override
	public CrudService<UIGlobalCategoryImage, EOGlobalCategoryImage, Long> getService() {
		return globalCategoryImageService;
	}
	
}
